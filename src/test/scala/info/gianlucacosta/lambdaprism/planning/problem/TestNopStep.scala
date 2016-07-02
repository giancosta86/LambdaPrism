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

package info.gianlucacosta.lambdaprism.planning.problem

import info.gianlucacosta.lambdaprism.logic.basic.formulas.{CompoundFunctor, Constant, Variable}
import org.scalatest.{FlatSpec, Matchers}

class TestNopStep extends FlatSpec with Matchers {
  val nopAction =
    NopAction(
      ~CompoundFunctor(
        "g",

        List(
          Variable("A"),
          Variable("B")
        )
      )
    )


  "NOP step's string representation" should "have the same format as a NOP action" in {
    val nopStep = nopAction.reify(
      Map(
        Variable("A") -> Constant("a"),
        Variable("B") -> Constant("b")
      )
    )

    nopStep.toString should be("NOP {~g(a, b)}")
  }
}
