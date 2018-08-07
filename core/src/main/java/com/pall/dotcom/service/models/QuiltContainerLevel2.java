package com.pall.dotcom.service.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.NodeIterator;

import org.apache.commons.lang3.StringUtils;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ValueMap;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author 3|Share - Upen
 *
 */
@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class QuiltContainerLevel2 {

    //private final String SLING_RESOURCE_TYPE = "sling:resourceType";
    private static final Logger LOG = LoggerFactory.getLogger(QuiltContainerLevel2.class);

    private static final Map<String, String[]> LAYOUTS;
    private static final Map<String, String[]> MOB_LAYOUTS;
    
    public static final String LAYOUT_1 = "1";
    
    public static final String LAYOUT_2 = "2";
    
    public static final String LAYOUT_3 = "3";
    
    public static final String LAYOUT_1_1 = "1x1";

    public static final String LAYOUT_1_2 = "1x2";

    public static final String LAYOUT_2_1 = "2x1";

    public static final String LAYOUT_2_2 = "2x2";
    
    public static final String FIELD_SIZE = "size";

    static {
        Map<String, String[]> aMap = new HashMap<String, String[]>();
        aMap.put("6", new String[] {LAYOUT_1_1,LAYOUT_2_2,LAYOUT_2_1,LAYOUT_2_2,LAYOUT_2_2,LAYOUT_2_1});
        aMap.put("7", new String[] {LAYOUT_2_1,LAYOUT_1_1,LAYOUT_2_2,LAYOUT_2_2,LAYOUT_2_2,LAYOUT_1_1,LAYOUT_1_1});
        aMap.put("8", new String[] {LAYOUT_2_2,LAYOUT_1_2,LAYOUT_2_2,LAYOUT_1_1,LAYOUT_2_1,LAYOUT_1_1,LAYOUT_2_1,LAYOUT_1_1});
        aMap.put("9a", new String[] {LAYOUT_1_1,LAYOUT_1_2,LAYOUT_1_1,LAYOUT_2_2,LAYOUT_2_2,LAYOUT_1_2,LAYOUT_1_1,LAYOUT_1_1,LAYOUT_1_1});
        aMap.put("9b", new String[] {LAYOUT_1_2,LAYOUT_2_2,LAYOUT_1_2,LAYOUT_1_1,LAYOUT_1_1,LAYOUT_1_2,LAYOUT_1_1,LAYOUT_2_1,LAYOUT_2_1});
        aMap.put("10", new String[] {LAYOUT_2_1,LAYOUT_1_1,LAYOUT_1_2,LAYOUT_1_1,LAYOUT_2_2,LAYOUT_2_1,LAYOUT_1_1,LAYOUT_1_1,LAYOUT_1_1,LAYOUT_2_1});
        aMap.put("11", new String[] {LAYOUT_1_1,LAYOUT_1_1,LAYOUT_1_1,LAYOUT_1_2,LAYOUT_1_1,LAYOUT_1_2,LAYOUT_1_1,LAYOUT_2_2,LAYOUT_1_1,LAYOUT_1_1,LAYOUT_2_1});
        aMap.put("12a", new String[] {LAYOUT_1_1,LAYOUT_1_2,LAYOUT_1_1,LAYOUT_1_1,LAYOUT_1_1,LAYOUT_1_1,LAYOUT_1_1,LAYOUT_1_1,LAYOUT_2_2,LAYOUT_2_1,LAYOUT_1_1,LAYOUT_1_1});
        aMap.put("12b", new String[] {LAYOUT_1_1,LAYOUT_1_1,LAYOUT_1_1,LAYOUT_1_2,LAYOUT_1_1,LAYOUT_1_2,LAYOUT_1_1,LAYOUT_2_2,LAYOUT_1_1,LAYOUT_1_1,LAYOUT_1_1,LAYOUT_1_1});
        LAYOUTS = Collections.unmodifiableMap(aMap);
    }

    static {
        Map<String, String[]> mobileMap = new HashMap<String, String[]>();
        mobileMap.put("6", new String[] {LAYOUT_1,LAYOUT_1,LAYOUT_1,LAYOUT_3,LAYOUT_3,LAYOUT_3});
        mobileMap.put("7", new String[] {LAYOUT_2,LAYOUT_2,LAYOUT_3,LAYOUT_3,LAYOUT_3,LAYOUT_2,LAYOUT_2});
        mobileMap.put("8", new String[] {LAYOUT_2,LAYOUT_2,LAYOUT_3,LAYOUT_2,LAYOUT_2,LAYOUT_3,LAYOUT_2,LAYOUT_2});
        mobileMap.put("9a", new String[] {LAYOUT_1,LAYOUT_1,LAYOUT_1,LAYOUT_3,LAYOUT_2,LAYOUT_2,LAYOUT_3,LAYOUT_2,LAYOUT_2});
        mobileMap.put("9b", new String[] {LAYOUT_2,LAYOUT_2,LAYOUT_2,LAYOUT_2,LAYOUT_3,LAYOUT_2,LAYOUT_2,LAYOUT_2,LAYOUT_2});
        mobileMap.put("10", new String[] {LAYOUT_2,LAYOUT_2,LAYOUT_1,LAYOUT_1,LAYOUT_1,LAYOUT_2,LAYOUT_2,LAYOUT_3,LAYOUT_2,LAYOUT_2});
        mobileMap.put("11", new String[] {LAYOUT_1,LAYOUT_1,LAYOUT_1,LAYOUT_1,LAYOUT_1,LAYOUT_1,LAYOUT_2,LAYOUT_2,LAYOUT_3,LAYOUT_2,LAYOUT_2});
        mobileMap.put("12a", new String[] {LAYOUT_1,LAYOUT_1,LAYOUT_1,LAYOUT_1,LAYOUT_1,LAYOUT_1,LAYOUT_2,LAYOUT_2,LAYOUT_3,LAYOUT_1,LAYOUT_1,LAYOUT_1});
        mobileMap.put("12b", new String[] {LAYOUT_1,LAYOUT_1,LAYOUT_1,LAYOUT_1,LAYOUT_1,LAYOUT_1,LAYOUT_2,LAYOUT_2,LAYOUT_1,LAYOUT_1,LAYOUT_1,LAYOUT_3});
        MOB_LAYOUTS = Collections.unmodifiableMap(mobileMap);
    }
    
    @Self
    private Resource container;

    @Inject
    private String layout;

    private String layoutText;

    private List<QuiltTile> tiles;
    
    private List<QuiltTile> mobileTiles;
    
    /**
     * This method identifies the size selected by the author or defaults to 6.
     * Reorders the tiles upon page refresh based on the selected layout and filters the qualified tiles (desktop & mobile) for the review mode.
     * 
     * @param  none
     * @return none
     */
    @PostConstruct
    public void init(){
        //Initially set the layout as 6
        if(StringUtils.isEmpty(layout)) {
            layout = "6";
            layoutText = "6 Tiles - 3 (2X2), 2 (2X1), 1 (1X1)";
        } else {
            layoutText = layout;
            if(layout.indexOf(' ')>-1)
                layout = layout.substring(0, layout.indexOf(' '));
        }
        if(null!=container){
            //reorder tiles to match the selected size
            reorderTiles();
            //Iterate the child nodes to fill the tiles list in the order based on the layout chosen
            categorizeTilesBySize();
        }
    }
    /**
     * This method reorders the quilt tile nodes under this container based on the layout selected.
     *
     * @param  none
     * @return none
     */
    private void reorderTiles() {
        if(!LAYOUTS.containsKey(layout)) 
            return;
        String[] order = LAYOUTS.get(layout);
        try {
            Node root = container.adaptTo(Node.class);
            NodeIterator itr = root.getNodes("quilt_tile*");
            List<Node> children = new ArrayList<Node>();
            while(itr.hasNext()) 
                children.add(itr.nextNode());
            List<String> nodes = new ArrayList<String>();
            //filter the first few tile nodes that are usable for the selected layout and add their names to a list.
            for(int i=0;i<order.length&&i<children.size();i++) {
                String size = order[i];
                for(int k=0;k<children.size();k++) {
                    Node node = children.get(k);
                    if(node==null) continue;
                    String name = node.getName();
                    String nodeSize = LAYOUT_1_1;
                    if(node.hasProperty(FIELD_SIZE))
                        nodeSize = node.getProperty(FIELD_SIZE).getValue().getString();
                    if(StringUtils.equals(size, nodeSize)) {
                        nodes.add(name);
                        children.set(k, null);
                        break;
                    }
                }
            }
            //check for any unusable tile nodes and add them to the end of the list
            for(Node node: children) {
                if(node!=null)
                    nodes.add(node.getName());
            }
            //Reorder the nodes under container based on the new ordering
            for(String s: nodes) 
                root.orderBefore(s, null); 
            
        } catch(Exception e) {
            LOG.error("Exception in reordering the nodes");
        }
    }

    /**
     * This method prepares the tiles list for the preview mode with the qualified tiles based on the layout selected.
     * It fills in blank tiles for the tiles that are not found from the authored ones.
     * It also populates the mobileTiles from the tiles list and manipulates the size field to match mobile sizes.
     * 
     * @param  none
     * @return none
     */
    private void categorizeTilesBySize(){
        Iterator<Resource> itr = container.getChildren().iterator();
        int i = 0;
        String[] order = LAYOUTS.get(layout);
        tiles = new ArrayList<QuiltTile>(order.length);
        while(itr.hasNext() && i<order.length){
            Resource res = itr.next();
            if(res!=null) {
                ValueMap props = res.adaptTo(ValueMap.class);
                String size = props.get(FIELD_SIZE, String.class);
                if(StringUtils.isEmpty(size))
                    size = LAYOUT_1_1;
                while(i<order.length && !StringUtils.equals(size, order[i])) {
                    QuiltTile tile = new QuiltTile();
                    tile.setSize(order[i]);
                    tiles.add(tile);
                    i++;
                }
                if(i<order.length) {
                    QuiltTile tile = res.adaptTo(QuiltTile.class);
                    tiles.add(tile);
                    i++;
                }
            }
        }
        if(tiles.size()!=order.length) {
            for(int j=tiles.size();j<order.length;j++) {
                QuiltTile tile = new QuiltTile();
                tile.setSize(order[j]);
                tiles.add(tile);
            }
        }
        
        //order for mobile layout tiles - clone the tiles in the desktop order and manipulate the size field only
        if(MOB_LAYOUTS.containsKey(layout)) {
            String[] mobileOrder = MOB_LAYOUTS.get(layout);
            mobileTiles = new ArrayList<QuiltTile>(mobileOrder.length);
            for(int j=0;j<mobileOrder.length;j++) {
                QuiltTile tile = tiles.size()>j?tiles.get(j).clone():new QuiltTile();
                tile.setSize(mobileOrder[j]);
                mobileTiles.add(tile);
            }
        }
    }
    public List<QuiltTile> getTiles(){
        return this.tiles;
    }

    public List<QuiltTile> getMobileTiles(){
        return this.mobileTiles;
    }

    public String getLayout() {
        return layout;
    }

    public String getLayoutText() {
        return layoutText;
    }

}
