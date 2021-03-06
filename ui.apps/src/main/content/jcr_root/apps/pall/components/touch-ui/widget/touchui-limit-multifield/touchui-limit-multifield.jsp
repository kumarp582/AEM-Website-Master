<%@ page import="com.adobe.granite.ui.components.Config" %>

<%@include file="/libs/granite/ui/global.jsp" %>

<%--include ootb multifield--%>
<sling:include resourceType="/libs/granite/ui/components/foundation/form/multifield"/>

<%
    Config mCfg = cmp.getConfig();

    Resource mField = mCfg.getChild("field");

    ValueMap mVM = mField.getParent().adaptTo(ValueMap.class);

    String mFieldLimitMin = mVM.get("fieldLimitMin", "");
	String mFieldLimitMax = mVM.get("fieldLimitMax", "");

    if(mFieldLimitMin.equals("") && mFieldLimitMax.equals("")){
        return;
    }

    String countValidatorId = "multifield-validator-" + System.currentTimeMillis(); //or some random number
%>

<%--
coral validation framework ignores hidden and contenteditable fields, so add an invisible text field
the text field is just for registering a validator
--%>
<input type=text style='display:none' id="<%=countValidatorId%>"/>

<script>
    (function($){
        var fieldErrorEl = $("<span class='coral-Form-fielderror coral-Icon coral-Icon--alert coral-Icon--sizeS' " +
                                "data-init='quicktip' data-quicktip-type='error' />");

        var $countValidatorField = $("#<%=countValidatorId%>"),
            $multifield = $countValidatorField.prev().find(".coral-Multifield"),
            fieldLimitMin = parseInt($multifield.data("fieldlimitmin")),
            fieldLimitMax = parseInt($multifield.data("fieldlimitmax")),
            count = $multifield.find(".coral-Multifield-input").length;


        $.validator.register({
            selector: $countValidatorField,
            validate: validate,
            show: show,
            clear: clear
        });

        $multifield.on("click", ".js-coral-Multifield-add", function (e) {
            ++count;
            adjustMultifieldUI();
        });

        $multifield.on("click", ".js-coral-Multifield-remove", function (e) {
            --count;
            adjustMultifieldUI();
        });

        function adjustMultifieldUI(){
            $countValidatorField.checkValidity();
            $countValidatorField.updateErrorUI();
        }

        function validate(){
            if(count >= fieldLimitMin && count <= fieldLimitMax){
                return null;
            }

            return "Limit set to " + fieldLimitMin + " - " + fieldLimitMax;
        }


        function clear(){
            $multifield.nextAll(".coral-Form-fielderror").tooltip("hide").remove();
        }

        function show($el, message){
            $multifield.nextAll(".coral-Form-fielderror").tooltip("hide").remove();

            var arrow = $multifield.closest("form").hasClass("coral-Form--vertical") ? "right" : "top";

            fieldErrorEl.clone()
                    .attr("data-quicktip-arrow", arrow)
                    .attr("data-quicktip-content", message)
                    .insertAfter($multifield);
        }

    })(jQuery);
</script>
