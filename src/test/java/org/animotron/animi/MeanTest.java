/*
 *  Copyright (C) 2011-2012 The Animo Project
 *  http://animotron.org
 *
 *  This file is part of Animotron.
 *
 *  Animotron is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of
 *  the License, or (at your option) any later version.
 *
 *  Animotron is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Affero General Public License for more details.
 *
 *  You should have received a copy of
 *  the GNU Affero General Public License along with Animotron.
 *  If not, see <http://www.gnu.org/licenses/>.
 */
package org.animotron.animi;

import org.animotron.statement.value.VALUE;
import org.animotron.utils.MessageDigester;
import org.junit.Test;
import org.neo4j.graphdb.Node;

import static org.animotron.expression.AnimoExpression.__;
import static org.junit.Assert.assertNotNull;

/**
 * @author <a href="mailto:shabanovd@gmail.com">Dmitriy Shabanov</a>
 * @author Eduard Khachukaev
 *
 */
public class MeanTest extends ATest {

	private void _(String name, String ... types) {
		StringBuilder sb = new StringBuilder();
		sb.append("def ").append(MessageDigester.uuid().toString());
		
		for (int i = 0; i < types.length; i++) {
			sb.append(" (").append(types[i]).append(")");
		}
		
		sb.append(" '").append(name).append("'.");
		
		System.out.println(sb.toString());
		
		__(sb.toString());
	}
	
	@Test
	public void test_00() throws Throwable {
		_("Петя", "image");

		Node n = VALUE._.get('П');
		assertNotNull(n);

		n = VALUE._.get("Петя");
		assertNotNull(n);
	}

	@Test
	public void test_01() throws Throwable {
	}

	@Test
	public void test_02() throws Throwable {
		__(
			"def image.", "def action.", "def pointer.", "def question.",
			"def time.", 
			"def time-from (time) (from).", 
			"def time-to (time) (to).", 
			"def time-shift (?time-from) (?time-to).",
			
			"def place.",
			"def place-from (place) (from).",
			"def place-to (place) (to).",
			"def place-shift (?place-from) (?place-to)."
		);
		
		//ОБРАЗЫ (24): 
//		_("я", "image");
//		_("он", "image");
//		_("она",  "image");
//		_("они", "image");

		_("Петя", "image");
//		_("луна", "image");
//		_("мальчик", "image");
//		_("небе", "image");
		
		_("деревне", "image", "place");
		_("школу", "image", "place");
		
//		_("домой", "image");
//		_("деревни", "image");
//		_("деревней", "image");
//			
//		_("тёмная", "image");
//		_("сторона", "image");
//		_("поезд", "image");
//		_("реки", "image");
//
//		_("неделю", "image", "time");
		_("утром", "image", "time");
//		_("днём", "image", "time");
//		_("полудню", "image", "time");
//		_("вечером", "image", "time");
//		_("ночью", "image", "time");
//		_("сегодня", "image", "time");
		
		//АКЦИИ (48): 
		_("пошёл", "action", "?time", "?place-shift");
		
//		_("взошла", "action");
//		_("появилась", "action");
//		_("знают", "action");
//		_("пойдёт", "action");
//		_("пришёл", "action");
//		_("появилось", "action");
//		_("сделал", "action");
//		_("делал", "action");
//		_("был", "action");
//		_("была",  "action");
		_("шёл", "action");
//		_("пошла", "action");
//		_("пошло", "action");
//		_("пошли", "action");
//		_("пришла", "action");
//		_("пришло", "action");
//		_("пришли", "action");
//		_("шла", "action");
//		_("шло", "action");
//		_("шли", "action");
//		_("сделала", "action");
//		_("делала", "action");
//		_("сделало", "action");
//		_("делало", "action");
//		_("сделали", "action");
//		_("делали", "action");
//		_("появились", "action");
//		_("появился", "action");
//		_("произошло", "action");
//		_("побежал", "action");
//		_("побежала", "action");
//		_("побежало", "action");
//		_("побежали", "action");
//		_("прибежал", "action");
//		_("прибежала", "action");
//		_("прибежало", "action");
//		_("прибежали", "action");
//		_("бежал", "action");
//		_("бежала", "action");
//		_("бежало", "action");
//		_("бежали", "action");
//		_("было", "action");
//		_("были",  "action");
//		_("положил", "action");
//		_("положила", "action");
//		_("положило", "action");
//		_("положили", "action");
		
		//УКАЗАТЕЛИ (7): 
		_("по", "pointer", "place");
		_("в", "pointer", "place-to");
//		_("к", "pointer");
//		_("спустя", "pointer");
//		_("за", "pointer");
//		_("из", "pointer");
//		_("возле", "pointer");

		//ВОПРОСИТЕЛИ (10): 
		_("когда", "question", "time");
		
		_("где", "question", "place");
		_("куда", "question", "place-to");
		_("откуда", "question", "place-from");
		
//		_("кто", "question");
//		_("что", "question");
//		
//		_("какой", "question");
//		_("какая", "question");
//		_("какое", "question");
//		_("какие", "question");
		
		testAnimi("Петя пошёл в школу по деревне утром.\n", "");
		testAnimi("где Петя?\n","Петя в школе.");
//		testAnimi("Вечером он пришёл домой.", "");
	}
	
    @Test
	public void test_10() throws Throwable {
    	__(
			"def id1 'Петя'.",
			"def id2 'развел'.",
			"def id3 'костер'.",
			"def id4 'в'.",
			"def id5 'лес'.",
			"def id6 (id4 id5) 'в лесу'.",
			"id1 id2 (id3) (id6)."
		);
	}

	
}
