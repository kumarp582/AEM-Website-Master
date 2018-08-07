(function () {
    var DATA_EAEM_NESTED = "data-eaem-complex-nested";
    var CFFW = ".coral-Form-fieldwrapper";

    function setSelect($field, value){
        var select = $field.closest(".coral-Select").data("select");

        if(select){
            select.setValue(value);
        }
    }

    function setCheckBox($field, value){
        $field.prop( "checked", $field.attr("value") === value);
    }

    //reads multifield data from server, creates the nested composite multifields and fills them
    function addDataInFields() {
        function getMultiFieldNames($multifields){
            var mNames = {}, mName;

            $multifields.each(function (i, multifield) {
                mName = $(multifield).children("[name$='@Delete']").attr("name");

                mName = mName.substring(0, mName.indexOf("@"));

                mName = mName.substring(2);

                mNames[mName] = $(multifield);
            });

            return mNames;
        }

        function buildMultiField(data, $multifield, mName){
            if(_.isEmpty(mName) || _.isEmpty(data)){
                return;
            }

            _.each(data, function(value, key){
                if(key === "jcr:primaryType"){
                    return;
                }

                $multifield.children(".js-coral-Multifield-add").click();

                _.each(value, function(fValue, fKey){
                    if(fKey === "jcr:primaryType"){
                        return;
                    }
                    var $field = $multifield.find("[name='./" + fKey + "']").last(),
                        type = $field.prop("type");
                    if(_.isEmpty($field)){
                        return;
                    }

                    //handle single selection dropdown
                    if( type === "select-one"){
                        setSelect($field, fValue);
                    }else if( type === "checkbox"){
                        setCheckBox($field, fValue);
                    }else{
                        $field.val(fValue);
                    }
                });
            });
        }

        $(document).on("dialog-ready", function() {
            var $multifields = $("[" + DATA_EAEM_NESTED + "]");

            if(_.isEmpty($multifields)){
                return;
            }

            var mNames = getMultiFieldNames($multifields),
                $form = $(".cq-dialog"),
                actionUrl = $form.attr("action") + ".infinity.json";

            function postProcess(data){
                _.each(mNames, function($multifield, mName){
                    buildMultiField(data[mName], $multifield, mName);
                });
                // Go through the second level
                var $multifieldsNew = $("[" + DATA_EAEM_NESTED + "]");

                $.each($multifieldsNew, function(index, item) {
                    var children = $(item).find('[data-eaem-nested]');

                    if(children.length > 0) {
                        var parentName = $(item).find('.coral-Form-fieldset').data("name");
                        if (parentName.indexOf("./") === 0) {
                            parentName = parentName.substring(2);
                        }
                        $.each(children, function(i, childMultifield){
                            var childName = $(childMultifield).data('nested-name');
                            buildMultiField(data[parentName][i+1][childName], $(childMultifield), childName);
                        });
                    }
                });
            }

            $.ajax(actionUrl).done(postProcess);
        });
    }

    //collect data from widgets in multifield and POST them to CRX
    function collectDataFromFields(){
        function fillValue($form, fieldSetName, $field, counter, parentName, parentCounter){
            var name = $field.attr("name");

            if (!name) {
                return;
            }

            //strip ./
            if (name.indexOf("./") === 0) {
                name = name.substring(2);
            }
            var nameAttribute = fieldSetName + "/" + counter + "/" + name;

            if(parentName != null) {
                //strip ./
                if (fieldSetName.indexOf("./") === 0) {
                    fieldSetName = fieldSetName.substring(2);
                }
                nameAttribute = parentName + "/" + parentCounter + "/" + fieldSetName + "/" + counter + "/" + name;
            }

            $('<input />').attr('type', 'hidden')
            .attr('name', nameAttribute)
            .attr('value', $field.val())
            .appendTo($form);

            //remove the field, so that individual values are not POSTed
            $field.remove();

        }

        $(document).on("click", ".cq-dialog-submit", function () {
            var $multifields = $("[" + DATA_EAEM_NESTED + "]");

            if(_.isEmpty($multifields)){
                return;
            }

            var $form = $(this).closest("form.foundation-form"),
                $fieldSets, $fields;

            $multifields.each(function(i, multifield){
                $fieldSets = $(multifield).find("[class='coral-Form-fieldset']");
                var firstLevelCount = 1;
                $fieldSets.each(function (counter, fieldSet) {
                    var $children = $(fieldSet).find("[class='coral-Form-fieldset']");
                    var firstLevelName = $(fieldSet).data("name");
                    var hasParent = $(fieldSet).parents("[class='coral-Form-fieldset']").length > 0;
                    $children.each(function (childIndex, childFieldSet) {

                        $fields = $(childFieldSet).children().children(CFFW);

                        $fields.each(function (j, field) {
                            fillValue($form, $(childFieldSet).data("name"), $(field).find("[name]"), (childIndex +1 ), firstLevelName, firstLevelCount);
                        });

                    });
                    $fields = $(fieldSet).children().children(CFFW);

                    $fields.each(function (j, field) {
                        fillValue($form, $(fieldSet).data("name"), $(field).find("[name]"), firstLevelCount);
                    });
                    if (!hasParent) {
                        firstLevelCount++;
                    }
                });
            });
        });
    }

    $(document).ready(function () {
        addDataInFields();
        collectDataFromFields();
    });
})();