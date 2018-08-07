<%@page session="false"
	import="
    org.apache.sling.api.resource.Resource,
    org.apache.sling.api.resource.ResourceUtil,
    org.apache.sling.api.resource.ValueMap,
    org.apache.sling.api.resource.ResourceResolver,
    org.apache.sling.api.resource.ResourceMetadata,
    org.apache.sling.api.wrappers.ValueMapDecorator,
    org.apache.commons.lang3.StringUtils,
    java.util.List,
    java.util.ArrayList,
    java.util.HashMap,
    java.util.Iterator,
    java.util.Locale,
    com.adobe.granite.ui.components.ds.DataSource,
    com.adobe.granite.ui.components.ds.EmptyDataSource,
    com.adobe.granite.ui.components.ds.SimpleDataSource,
    com.adobe.granite.ui.components.ds.ValueMapResource"%>
<%@taglib prefix="cq" uri="http://www.day.com/taglibs/cq/1.0"%>
<cq:defineObjects />
<%
    request.setAttribute(DataSource.class.getName(), EmptyDataSource.instance());
    Locale locale = request.getLocale();
    Resource datasource = resource.getChild("datasource");
    ResourceResolver resolver = resource.getResourceResolver();
    ValueMap dsProperties = ResourceUtil.getValueMap(datasource);
    String genericListPath = dsProperties.get("path", String.class);
    String textName = dsProperties.get("text", String.class);
    String valueName = dsProperties.get("value", String.class);
    // If the path isn't null,  get the resource and loop through the children.
    if (genericListPath != null) {
        Resource parentResource = resourceResolver.getResource(genericListPath);
        // Create a list to stuff our values
        List<Resource> fakeResourceList = new ArrayList<Resource>();
        // Grab the children and get their properties.
        Iterator<Resource> itr = parentResource.listChildren();
        while (itr.hasNext()) {
            Resource child = itr.next();
            ValueMap vm = new ValueMapDecorator(new HashMap<String, Object>());
            ValueMap childProps = ResourceUtil.getValueMap(child);
            String txt = childProps.get(textName, String.class);
            String val = childProps.get(valueName, String.class);
            vm.put("text", StringUtils.isEmpty(txt)?child.getName():txt);
            vm.put("value", StringUtils.isEmpty(val)?child.getName():val);
            fakeResourceList.add(new ValueMapResource(resolver, new ResourceMetadata(), "nt:unstructured", vm));
        }
        // Create a new data source from iterating through our fakedResourceList
        DataSource ds = new SimpleDataSource(fakeResourceList.iterator());
        // Add the datasource to our request to expose in the view
        request.setAttribute(DataSource.class.getName(), ds);
    }
%>