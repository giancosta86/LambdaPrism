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


class TestStrictClosedWorldValidator extends FlatSpec with Matchers {

  "Validator" should "accept a problem having only positive literals" in {
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
              Variable("A")
            ),

            List(
              Constant("d")
            ),

            List(
              Variable("A")
            )
          )
        )
      )


    ProblemValidators.StrictClosedWorldValidator(
      problem
    )
  }


  "Validator" should "refuse negative start literals" in {
    val problem =
      Problem(
        List(
          ~Constant("b")
        ),

        List(
          Constant("c")
        ),

        List(
          MainAction(
            "op",

            List(
              Variable("A")
            ),

            List(
              Constant("d")
            ),

            List(
              Variable("A")
            )
          )
        )
      )


    intercept[IllegalArgumentException] {
      ProblemValidators.StrictClosedWorldValidator(
        problem
      )
    }
  }


  "Validator" should "refuse negative goal literals" in {
    val problem =
      Problem(
        List(
          Constant("b")
        ),

        List(
          ~Constant("c")
        ),

        List(
          MainAction(
            "op",

            List(
              Variable("A")
            ),

            List(
              Constant("d")
            ),

            List(
              Variable("A")
            )
          )
        )
      )


    intercept[IllegalArgumentException] {
      ProblemValidators.StrictClosedWorldValidator(
        problem
      )
    }
  }



  "Validator" should "refuse negative main action preconditions" in {
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
              Variable("A")
            ),

            List(
              ~Constant("d")
            ),

            List(
              Variable("A")
            )
          )
        )
      )


    intercept[IllegalArgumentException] {
      ProblemValidators.StrictClosedWorldValidator(
        problem
      )
    }
  }




  "Validator" should "accept negative main action effects" in {
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
              Variable("A")
            ),

            List(
              Constant("d")
            ),

            List(
              ~Variable("A")
            )
          )
        )
      )


    ProblemValidators.StrictClosedWorldValidator(
      problem
    )
  }
}
