/*
Copyright 2008 WebAtlas
Authors : Mathieu Bastian, Mathieu Jacomy, Julian Bilcke
Website : http://www.gephi.org

This file is part of Gephi.

Gephi is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Gephi is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Gephi.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.gephi.io.generator.plugin;

import org.gephi.data.attributes.api.AttributeColumn;
import org.gephi.data.attributes.api.AttributeType;
import org.gephi.data.attributes.type.TimeInterval;
import org.gephi.io.generator.spi.Generator;
import org.gephi.io.generator.spi.GeneratorUI;
import org.gephi.io.importer.api.ContainerLoader;
import org.gephi.io.importer.api.EdgeDraft;
import org.gephi.io.importer.api.NodeDraft;
import org.gephi.utils.progress.ProgressTicket;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Mathieu Bastian
 */
@ServiceProvider(service = Generator.class)
public class DynamicGraph implements Generator {

    protected int numberOfNodes = 2000;
    protected int numberOfEdges = 20000;

    public void generate(ContainerLoader container) {

        ContainerLoader.DraftFactory factory = container.factory();
        AttributeColumn col = container.getAttributeModel().getNodeTable().addColumn("dynamicrange", AttributeType.TIME_INTERVAL);

        for (int i = 0; i < numberOfNodes; i++) {
            NodeDraft nodeDraft = factory.newNodeDraft();
            nodeDraft.setLabel("Node " + i);
            nodeDraft.setId("Node " + i);
            double from = (double) Math.random();
            double to = (double) (from + (1.0 - from) * Math.random());
            nodeDraft.addAttributeValue(col, new TimeInterval(from, to));
            container.addNode(nodeDraft);
        }

        for (int i = 0; i < numberOfEdges; i++) {
            EdgeDraft edgeDraft = factory.newEdgeDraft();
            int source = (int) (Math.random() * (numberOfNodes));
            int target = (int) (Math.random() * (numberOfNodes));
            edgeDraft.setSource(container.getNode("Node " + source));
            edgeDraft.setTarget(container.getNode("Node " + target));
            container.addEdge(edgeDraft);
        }
    }

    public String getName() {
        return "Dynamic Graph";
    }

    public GeneratorUI getUI() {
        return null;
    }

    public boolean cancel() {
        return false;
    }

    public void setProgressTicket(ProgressTicket progressTicket) {
    }
}
