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


import android.test.ActivityInstrumentationTestCase2;

import org.catrobat.catroid.R;
import org.catrobat.catroid.ui.MainMenuActivity;
import org.junit.Before;

import static android.support.test.espresso.Espresso.*;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

public class ExampleEspressoUiTest extends ActivityInstrumentationTestCase2<MainMenuActivity> {

	private MainMenuActivity mActivity;

	public ExampleEspressoUiTest() {
		super(MainMenuActivity.class);
	}

	@Before
	public void setUp() throws Exception {
		super.setUp();
		// injectInstrumentation(InstrumentationRegistry.getInstrumentation());
		mActivity = getActivity();
	}

	public void testChangeText_sameActivity() {
		// Type text and then press the button.
		//onView(withId()).perform(typeText(STRING_TO_BE_TYPED), closeSoftKeyboard());
		//onView(withId(R.id.changeTextButton)).perform(click());
		onView(withId(R.id.bottom_bar)).perform(click());
		// Check that the text was changed.

	}

}
