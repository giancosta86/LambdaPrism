/*§
  ===========================================================================
  LambdaPrism
  ===========================================================================
  Copyright (C) 2016 Gianluca Costa
  ===========================================================================
  Licensed under the Apache License, Version 2.0 (the "License");
  you may not use this file except in compliance with the License.
  You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
  ===========================================================================
*/

package info.gianlucacosta.lambdaprism.logic.basic.formulas

import org.scalatest.{FlatSpec, Matchers}

abstract class TestFormula[T <: Formula] extends FlatSpec with Matchers {

  "Ground formula" should "be detected" in {
    val groundFormula = createGroundFormula()

    groundFormula.isGround should be(true)
  }


  "Non-ground formula" should "be detected" in {
    val nonGroundFormula = createNonGroundFormula()

    nonGroundFormula.isGround should be(false)
  }


  protected def createGroundFormula(): T

  protected def createNonGroundFormula(): T
}
