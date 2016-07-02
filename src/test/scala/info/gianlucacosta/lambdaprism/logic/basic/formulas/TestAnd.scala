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

class TestAnd extends TestFormula[And] {
  "And" should "require at least 2 operands" in {
    intercept[IllegalArgumentException] {
      And(
        Variable("X")
      )
    }
  }

  "And" should "support 2 operands" in {
    And(
      Variable("X"),
      Variable("Y")
    )
  }


  "And" should "support 3 or more operands" in {
    And(
      Variable("X"),
      Variable("Y"),
      Variable("Z")
    )
  }


  override protected def createGroundFormula(): And =
    And(
      Constant("a"),
      Constant("b")
    )


  override protected def createNonGroundFormula(): And =
    And(
      Constant("a"),

      CompoundFunctor(
        "f",
        List(
          Constant("b"),
          Variable("X"),
          Constant("c")
        )
      )
    )
}
