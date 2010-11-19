/**
 * Copyright (c) 2002-2010 "Neo Technology,"
 * Network Engine for Objects in Lund AB [http://neotechnology.com]
 *
 * This file is part of Neo4j.
 *
 * Neo4j is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.neo4j.kernel.impl.traversal;

import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.traversal.Evaluators;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.kernel.Traversal;

public class DepthOneTraversalTest extends AbstractTestBase
{
    @BeforeClass
    public static void createTheGraph()
    {
        createGraph( "0 ROOT 1", "1 KNOWS 2", "2 KNOWS 3", "2 KNOWS 4",
                "4 KNOWS 5", "5 KNOWS 6", "3 KNOWS 1" );
    }
    
    private void shouldGetBothNodesOnDepthOne( TraversalDescription description )
    {
        description = description.evaluator( Evaluators.atDepth( 1 ) );
        expectNodes( description.traverse( getNodeWithName( "3" ) ), "1", "2" );
    }
    
    @Test
    public void shouldGetBothNodesOnDepthOneForDepthFirst()
    {
        shouldGetBothNodesOnDepthOne( Traversal.description().depthFirst() );
    }

    @Test
    public void shouldGetBothNodesOnDepthOneForBreadthFirst()
    {
        shouldGetBothNodesOnDepthOne( Traversal.description().breadthFirst() );
    }
}
