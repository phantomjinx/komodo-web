/*
 * JBoss, Home of Professional Open Source.
 * See the COPYRIGHT.txt file distributed with this work for information
 * regarding copyright ownership.  Some portions may be licensed
 * to Red Hat, Inc. under one or more contributor license agreements.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301 USA.
 */
package org.komodo.web.client.panels.vdb.editor.diag.tree;

import org.komodo.web.client.panels.vdb.editor.diag.DiagramCss;
import org.komodo.web.share.Constants;
import com.github.gwtd3.api.Coords;
import com.github.gwtd3.api.D3;
import com.github.gwtd3.api.arrays.Array;
import com.github.gwtd3.api.arrays.ForEachCallback;
import com.github.gwtd3.api.core.Selection;
import com.github.gwtd3.api.core.Transition;
import com.github.gwtd3.api.core.UpdateSelection;
import com.github.gwtd3.api.core.Value;
import com.github.gwtd3.api.functions.DatumFunction;
import com.github.gwtd3.api.functions.KeyFunction;
import com.github.gwtd3.api.layout.HierarchicalLayout.Node;
import com.github.gwtd3.api.layout.Link;
import com.github.gwtd3.api.layout.Tree;
import com.github.gwtd3.api.svg.Diagonal;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.dom.client.Element;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 */
public class TreeCanvas implements Constants {

    private static class JSTreeNode extends Node {
        protected JSTreeNode() {
            super();
        }

        protected final native int id() /*-{
            return this.id || -1;
        }-*/;

        protected final native int id(int id) /*-{
            return this.id = id;
        }-*/;

        protected final native void setAttr(String name, JavaScriptObject value) /*-{
            this[name] = value;
        }-*/;

        protected final native double setAttr(String name, double value) /*-{
            return this[name] = value;
        }-*/;

        protected final native JavaScriptObject getObjAttr(String name) /*-{
            return this[name];
        }-*/;

        protected final native double getNumAttr(String name) /*-{
            return this[name];
        }-*/;
    }

    private class Click implements DatumFunction<Void> {
        @Override
        public Void apply(Element context, Value d, int index) {
            JSTreeNode node = d.<JSTreeNode> as();
            if (node.children() != null) {
                node.setAttr("_children", node.children());
                node.setAttr("children", null);
            } else {
                node.setAttr("children", node.getObjAttr("_children"));
                node.setAttr("_children", null);
            }
            update(node);
            return null;
        }
    }

    private class Collapse implements ForEachCallback<Void> {
        @Override
        public Void forEach(Object thisArg, Value element, int index,
                Array<?> array) {
            JSTreeNode datum = element.<JSTreeNode> as();
            Array<Node> children = datum.children();
            if (children != null) {
                datum.setAttr("_children", children);
                datum.getObjAttr("_children").<Array<Node>> cast()
                        .forEach(this);
                datum.setAttr("children", null);
            }
            return null;
        }
    }

    private final Widget parent;

    private final Selection svg;

    private final Tree layout;

    private final Diagonal diagonal;

    private final DiagramCss css;

    private int parentWidth;

    private int parentHeight;

    private TreeData data;

    private int i = 0;

    private final int duration = 750;

    private JSTreeNode root;

    /**
     * @param widget the widget to place the canvas on
     * @param width the initial width of the widget
     * @param height the initial height of the widget
     * @param css the css settings to be observed by the diagram
     */
    public TreeCanvas(Widget widget, int width, int height, DiagramCss css) {
        this.parent = widget;
        this.parentWidth = width;
        this.parentHeight = height;
        this.css = css;

        layout = D3.layout().tree().size(width, height);

        // set the global way to draw paths
        // TODO - What does this do exactly??
        diagonal = D3.svg().diagonal().projection(new DatumFunction<Array<Double>>() {
            @Override
            public Array<Double> apply(Element context, Value d, int index) {
                JSTreeNode data = d.<JSTreeNode> as();
                return Array.fromDoubles(data.x(), data.y());
            }
        });

        // Create the svg canvas by selecting the 'div' of the widget and
        // appending an 'svg' div and inside that a 'g' div
        this.svg = D3.select(parent)
                    .append(SVG_ELEMENT)
                    .append(GROUP_ELEMENT);
    }

    public void setRoot(TreeData root) {
        this.data = root;
    }

    /**
     * 
     */
    public void update() {
        if(this.data == null)
            return;

        String definition = this.data.toDefinition();

        root = JSONParser.parseLenient(definition).isObject().getJavaScriptObject().<JSTreeNode> cast();
        root.setAttr("x0", (parentWidth - 20) / 2);
        root.setAttr("y0", 20);
        if (root.children() != null) {
            root.children().forEach(new Collapse());
        }

        update(root);
    }

    public void update(final JSTreeNode source) {
        // Compute the new tree layout.
        Array<Node> nodes = layout.nodes(root).reverse();
        Array<Link> links = layout.links(nodes);

        // normalize depth
        nodes.forEach(new ForEachCallback<Void>() {
            @Override
            public Void forEach(Object thisArg, Value element, int index,
                    Array<?> array) {
                JSTreeNode datum = element.<JSTreeNode> as();
                datum.setAttr("y", datum.depth() * 180);
                return null;
            }
        });

        // assign ids to nodes
        UpdateSelection node = svg.selectAll("g." + css.node()).data(nodes,
                new KeyFunction<Integer>() {
                    @Override
                    public Integer map(Element context, Array<?> newDataArray,
                                                    Value datum, int index) {
                        JSTreeNode d = datum.<JSTreeNode> as();
                        return ((d.id() == -1) ? d.id(++i) : d.id());
                    }
                });

        // add click function on node click
        Selection nodeEnter = node
                .enter()
                .append("g")
                .attr("class", css.node())
                .attr("transform",
                        "translate(" + source.getNumAttr("x0") + ","
                                + source.getNumAttr("y0") + ")")
                .on("click", new Click());

        // add circles to all entering nodes
        nodeEnter.append("circle").attr("r", 1e-6)
                .style("fill", new DatumFunction<String>() {
                    @Override
                    public String apply(Element context, Value d, int index) {
                        JavaScriptObject node = d.<JSTreeNode> as()
                                .getObjAttr("_children");
                        return (node != null) ? "lightsteelblue" : "#fff";
                    }
                });

        // transition entering nodes
        Transition nodeUpdate = node.transition().duration(duration)
                .attr("transform", new DatumFunction<String>() {
                    @Override
                    public String apply(Element context, Value d, int index) {
                        JSTreeNode data = d.<JSTreeNode> as();
                        return "translate(" + data.x() + "," + data.y() + ")";
                    }
                });

        nodeUpdate.select("circle").attr("r", 4.5)
                .style("fill", new DatumFunction<String>() {
                    @Override
                    public String apply(Element context, Value d, int index) {
                        JavaScriptObject object = d.<JSTreeNode> as()
                                .getObjAttr("_children");
                        return (object != null) ? "lightsteelblue" : "#fff";
                    }
                });

        // transition exiting nodes
        Transition nodeExit = node.exit().transition().duration(duration)
                .attr("transform", new DatumFunction<String>() {
                    @Override
                    public String apply(Element context, Value d, int index) {
                        return "translate(" + source.x() + "," + source.y()
                                + ")";
                    }
                }).remove();

        nodeExit.select("circle").attr("r", 1e-6);

        // update svg paths for new node locations
        UpdateSelection link = svg.selectAll("path." + css.link()).data(links,
                new KeyFunction<Integer>() {
                    @Override
                    public Integer map(Element context, Array<?> newDataArray,
                            Value datum, int index) {
                        return datum.<Link> as().target().<JSTreeNode> cast()
                                .id();
                    }
                });

        link.enter().insert("svg:path", "g").attr("class", css.link())
                .attr("d", new DatumFunction<String>() {
                    @Override
                    public String apply(Element context, Value d, int index) {
                        Coords o = Coords.create(source.getNumAttr("x0"),
                                source.getNumAttr("y0"));
                        return diagonal.generate(Link.create(o, o));
                    }
                });

        link.transition().duration(duration).attr("d", diagonal);

        link.exit().transition().duration(duration)
                .attr("d", new DatumFunction<String>() {
                    @Override
                    public String apply(Element context, Value d, int index) {
                        Coords o = Coords.create(source.x(), source.y());
                        return diagonal.generate(Link.create(o, o));
                    }
                }).remove();

        // update locations on node
        nodes.forEach(new ForEachCallback<Void>() {
            @Override
            public Void forEach(Object thisArg, Value element, int index,
                    Array<?> array) {
                JSTreeNode data = element.<JSTreeNode> as();
                data.setAttr("x0", data.x());
                data.setAttr("y0", data.y());
                return null;
            }
        });
    }
}
