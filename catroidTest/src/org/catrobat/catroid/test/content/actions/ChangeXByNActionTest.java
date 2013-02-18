/**
 *  Catroid: An on-device visual programming system for Android devices
 *  Copyright (C) 2010-2013 The Catrobat Team
 *  (<http://developer.catrobat.org/credits>)
 *  
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU Affero General Public License as
 *  published by the Free Software Foundation, either version 3 of the
 *  License, or (at your option) any later version.
 *  
 *  An additional term exception under section 7 of the GNU Affero
 *  General Public License, version 3, is available at
 *  http://developer.catrobat.org/license_additional_term
 *  
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU Affero General Public License for more details.
 *  
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.test.content.actions;

import org.catrobat.catroid.content.Sprite;
import org.catrobat.catroid.content.actions.ChangeXByNAction;
import org.catrobat.catroid.content.actions.ExtendedActions;

import android.test.AndroidTestCase;

public class ChangeXByNActionTest extends AndroidTestCase {

	private int xMovement = 100;

	public void testNormalBehavior() {
		Sprite sprite = new Sprite("testSprite");
		assertEquals("Unexpected initial sprite x position", 0f, sprite.look.getXPosition());
		assertEquals("Unexpected initial sprite y position", 0f, sprite.look.getYPosition());

		int xPosition = (int) sprite.look.getXPosition();

		ChangeXByNAction action = ExtendedActions.changeXByN(sprite, xMovement);
		sprite.look.addAction(action);
		action.act(1.0f);

		xPosition += xMovement;
		assertEquals("Incorrect sprite x position after ChangeXByNBrick executed", (float) xPosition,
				sprite.look.getXPosition());
	}

	public void testNullSprite() {
		ChangeXByNAction action = ExtendedActions.changeXByN(null, xMovement);
		try {
			action.act(1.0f);
			fail("Execution of ChangeXByNBrick with null Sprite did not cause a " + "NullPointerException to be thrown");
		} catch (NullPointerException expected) {
			// expected behavior
		}
	}

	public void testBoundaryPositions() {
		Sprite sprite = new Sprite("testSprite");

		int xPosition = 10;
		sprite.look.setXYPosition(xPosition, sprite.look.getYPosition());

		ChangeXByNAction action = ExtendedActions.changeXByN(sprite, Integer.MAX_VALUE);
		sprite.look.addAction(action);
		action.act(1.0f);

		assertEquals("ChangeXByNBrick failed to place Sprite at maximum x integer value", Integer.MAX_VALUE,
				(int) sprite.look.getXPosition());

		xPosition = -10;
		sprite.look.setXYPosition(xPosition, sprite.look.getYPosition());

		action = ExtendedActions.changeXByN(sprite, Integer.MIN_VALUE);
		sprite.look.addAction(action);
		action.act(1.0f);

		assertEquals("ChangeXByNBrick failed to place Sprite at minimum x integer value", Integer.MIN_VALUE,
				(int) sprite.look.getXPosition());

	}
}
