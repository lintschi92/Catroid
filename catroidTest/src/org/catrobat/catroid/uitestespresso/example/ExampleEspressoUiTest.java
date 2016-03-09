/*
 * Catroid: An on-device visual programming system for Android devices
 * Copyright (C) 2010-2016 The Catrobat Team
 * (<http://developer.catrobat.org/credits>)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * An additional term exception under section 7 of the GNU Affero
 * General Public License, version 3, is available at
 * http://developer.catrobat.org/license_additional_term
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.catrobat.catroid.uitestespresso.example;


import android.support.test.InstrumentationRegistry;
import android.test.ActivityInstrumentationTestCase2;
import org.catrobat.catroid.R;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.catrobat.catroid.uitest.util.UiTestUtils;
import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class ExampleEspressoUiTest extends ActivityInstrumentationTestCase2<MainMenuActivity> {

	private MainMenuActivity mActivity;

	public ExampleEspressoUiTest() {
		super(MainMenuActivity.class);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		// do own setup after super.setup()
		injectInstrumentation(InstrumentationRegistry.getInstrumentation());
		mActivity = getActivity();
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();

		// called after each test

		// flow of 2 tests:
		// test class constructor,
		// setUp,
		// testCase1,
		// tearDown,
		// setUp,
		// testCase2,
		// tearDown...
	}

	public void testSimpleExampleTestPass() {

		// testMethods must start with "testXXXX" where XXXX is the last part of the function name
		// every test must have the "@Test" annotation
		UiTestUtils.createEmptyProject();
		onView(withId(R.id.main_menu_button_new)).perform(click());
	}

	public void testSimpleExampleTestFail() {

		// testMethods must start with "testXXXX" where XXXX is the last part of the function name
		// every test must have the "@Test" annotation
		UiTestUtils.createEmptyProject();
		onView(withId(R.id.brick_add_item_to_userlist_edit_text)).perform(click());
	}

}
