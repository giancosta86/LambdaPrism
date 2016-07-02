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

import info.gianlucacosta.lambdaprism.logic.basic.formulas.{Constant, Variable}
import org.scalatest.{FlatSpec, Matchers}

class TestPropositionalValidator extends FlatSpec with Matchers {
  "Validator" should "accept a problem with only ground literals" in {
    val problem =
      Problem(
        List(
          Constant("b")
        ),

        List(
          Constant("c")
        ),

        List(
          MainAction(
            "op",

            List(),

            List(
              Constant("d")
            ),

            List(
              Constant("a")
            )
          )
        )
      )


    ProblemValidators.PropositionalValidator(problem)
  }


  "Validator" should "refuse non-ground literals start literals" in {
    val problem =
      Problem(
        List(
          Constant("b"),
          Variable("A")
        ),

        List(
          Constant("c")
        ),

        List(
          MainAction(
            "op",

            List(),

            List(
              Constant("d")
            ),

            List(
              Constant("a")
            )
          )
        )
      )

    intercept[IllegalArgumentException] {
      ProblemValidators.PropositionalValidator(problem)
    }
  }


  "Validator" should "refuse non-ground goal literals" in {
    val problem =
      Problem(
        List(
          Constant("b"),
          Constant("a")
        ),

        List(
          Variable("X")
        ),

        List(
          MainAction(
            "op",

            List(),

            List(
              Constant("d")
            ),

            List(
              Constant("a")
            )
          )
        )
      )

    intercept[IllegalArgumentException] {
      ProblemValidators.PropositionalValidator(problem)
    }
  }



  "Validator" should "accept a problem with non-ground literals in main actions" in {
    val problem =
      Problem(
        List(
          Constant("b")
        ),

        List(
          Constant("c")
        ),

        List(
          MainAction(
            "op",

            List(
              Variable("A"),
              Variable("D")
            ),

            List(
              Variable("D")
            ),

            List(
              Variable("A")
            )
          )
        )
      )


    ProblemValidators.PropositionalValidator(problem)
  }
}
