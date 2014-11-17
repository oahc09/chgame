/*******************************************************************************
 * Copyright 2014 See AUTHORS file.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.oahcfly.chgame.test.src.com.badlogic.gdx.ai.tests.btree.dog;

import com.sun.xml.internal.ws.api.addressing.WSEndpointReference.Metadata;

/** @author implicit-invocation
 * @author davebaol */
public class BarkTask extends LeafTask<Dog> {

	public static final Metadata METADATA = new Metadata(LeafTask.METADATA, "times");

	public int times = 1;

	@Override
	public void run (Dog dog) {
		for (int i = 0; i < times; i++)
			dog.bark();
		success();
	}

	@Override
	protected Task<Dog> copyTo (Task<Dog> task) {
		BarkTask bark = (BarkTask)task;
		bark.times = times;

		return task;
	}

}
