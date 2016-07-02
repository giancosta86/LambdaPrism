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

import info.gianlucacosta.lambdaprism.logic.basic.formulas.Constant
import org.scalatest.{FlatSpec, Matchers}


class TestProblem extends FlatSpec with Matchers {
  "A problem" should "be correctly created" in {
    Problem(
      List(
        Constant("a")
      ),

      List(
        Constant("b")
      ),

      List(
        MainAction(
          "op",
          List(),
          List(),
          List()
        )
      )
    )
  }


  "A problem" should "have at least a start literal" in {
    intercept[IllegalArgumentException] {
      Problem(
        List(),

        List(
          Constant("b")
        ),

        List()
      )
    }
  }


  "A problem" should "have at least a goal literal" in {
    intercept[IllegalArgumentException] {
      Problem(
        List(
          Constant("a")
        ),

        List(),

        List()
      )
    }
  }


  "A problem" should "have main actions having unique names" in {
    intercept[IllegalArgumentException] {
      Problem(
        List(
          Constant("a")
        ),

        List(
          Constant("b")
        ),

        List(
          MainAction(
            "op",
            List(),
            List(),
            List()
          ),

          MainAction(
            "op",
            List(),
            List(),
            List()
          )
        )
      )
    }
  }
}
