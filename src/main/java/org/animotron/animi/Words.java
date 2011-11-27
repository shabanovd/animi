/*
 *  Copyright (C) 2011 The Animo Project
 *  http://animotron.org
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 3
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
 */
package org.animotron.animi;

import org.animotron.graph.AnimoGraph;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.graphdb.index.IndexManager;
import org.neo4j.graphdb.index.RelationshipIndex;

/**
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 *
 */
public class Words {
	
	public static final Words _ = new Words();
	
	private static final String NAME = "words";

	private static RelationshipIndex INDEX;

	private Words() {
		
		IndexManager indexManager = AnimoGraph.getDb().index();

		INDEX = indexManager.forRelationships(NAME);
	}
	
	public void add(Relationship the, String word) {
		INDEX.add(the, NAME, word);
	}

	public IndexHits<Relationship> search(String word) {
		return INDEX.get(NAME, word);
	}
}