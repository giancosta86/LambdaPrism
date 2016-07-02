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


class TestForAll extends TestFormula[ForAll] {
  "For All" should "be instantiated as a case class" in {
    ForAll(Variable("X"))(
      And(
        Variable("X"),
        Variable("Y")
      )
    )
  }

  override protected def createGroundFormula(): ForAll =
    ForAll(Variable("X"))(
      Constant("a")
    )


  override protected def createNonGroundFormula(): ForAll =
    ForAll(Variable("X"))(
      CompoundFunctor(
        "f",

        List(
          Constant("a"),
          Variable("X"),
          Constant("b")
        )
      )
    )
}
