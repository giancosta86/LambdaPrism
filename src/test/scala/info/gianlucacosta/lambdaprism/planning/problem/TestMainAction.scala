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

import info.gianlucacosta.lambdaprism.logic.basic.formulas._
import org.scalatest.{FlatSpec, Matchers}

class TestMainAction extends FlatSpec with Matchers {
  "Action name" should "NOT be null" in {
    intercept[IllegalArgumentException] {
      new MainAction(
        null,
        List(),
        List(),
        List()
      )
    }
  }

  "Action name" should "NOT be empty" in {
    intercept[IllegalArgumentException] {
      new MainAction(
        "",
        List(),
        List(),
        List()
      )
    }
  }


  "Action name" should "NOT match the Start action name" in {
    intercept[IllegalArgumentException] {
      new MainAction(
        StartAction.Name,
        List(),
        List(),
        List()
      )
    }
  }


  "Action name" should "NOT match the Goal action name" in {
    intercept[IllegalArgumentException] {
      new MainAction(
        GoalAction.Name,
        List(),
        List(),
        List()
      )
    }
  }


  "Action name" should "NOT be uppercase" in {
    intercept[IllegalArgumentException] {
      new MainAction(
        "TestAction",
        List(),
        List(),
        List()
      )
    }
  }


  "Action" should "be allowed to have no parameters and no preconditions/effects" in {
    new MainAction(
      "testAction",
      List(),
      List(),
      List()
    )
  }


  "Parameters" should "NOT be declared if they do not appear in the preconditions/effects" in {
    intercept[IllegalArgumentException] {
      new MainAction(
        "testAction",

        List(
          Variable("Alpha")
        ),

        List(
          CompoundFunctor(
            "f",
            List(
              Variable("Beta")
            )
          )
        ),

        List(
          CompoundFunctor(
            "f",
            List(
              Variable("Gamma")
            )
          )
        )
      )
    }
  }


  "Variables" should "not necessarily be declared as parameters" in {
    new MainAction(
      "testAction",

      List(
        Variable("Alpha")
      ),

      List(
        CompoundFunctor(
          "f",
          List(
            Variable("Beta")
          )
        )
      ),

      List(
        CompoundFunctor(
          "f",
          List(
            Variable("Alpha"),
            Constant("omega"),
            Variable("Gamma")
          )
        )
      )
    )
  }



  "Variables" should "include variables from both preconditions and effects" in {
    val action = new MainAction(
      "testAction",

      List(
        Variable("Alpha")
      ),

      List(
        CompoundFunctor(
          "f",
          List(
            Variable("Beta"),
            Variable("Gamma")
          )
        )
      ),

      List(
        CompoundFunctor(
          "f",
          List(
            Variable("Alpha"),
            Constant("omega"),
            Variable("Gamma")
          )
        )
      )
    )

    action.variables should be(Set(
      Variable("Alpha"),
      Variable("Beta"),
      Variable("Gamma")
    ))
  }


  "Preconditions" should "NOT have contradictions" in {
    intercept[IllegalArgumentException] {
      MainAction(
        "myAction",
        List(),

        List(
          Constant("a"),

          ~Constant("a")
        ),

        List()
      )
    }
  }


  "Effects" should "NOT have contradictions" in {
    intercept[IllegalArgumentException] {
      MainAction(
        "myAction",
        List(),

        List(),

        List(
          Constant("a"),
          ~Constant("a")
        )
      )
    }
  }


  "Reify" should "work if there are no variables" in {
    val action = MainAction(
      "op",
      List(),
      List(
        Constant("a")
      ),

      List(
        Constant("b")
      )
    )


    val step = action.reify(Map[Variable, Argument]())

    step.action should be(action)

    step.arguments should be(empty)

    step.preconditions should be(
      List(
        Constant("a")
      )
    )

    step.effects should be(
      List(
        Constant("b")
      )
    )
  }


  "Reify" should "work if the provided variables are bound to constants" in {
    val action = MainAction(
      "op",
      List(
        Variable("B")
      ),

      List(
        Variable("A"),
        CompoundFunctor(
          "f",

          List(
            Variable("B"),
            Constant("9"),
            Variable("A")
          )
        )
      ),

      List(
        Variable("C")
      )
    )


    val step = action.reify(Map(
      Variable("A") -> Constant("a"),
      Variable("B") -> Constant("b"),
      Variable("C") -> Constant("c")
    ))


    step.action should be(action)

    step.arguments should be(List(
      Constant("b")
    ))

    step.preconditions should be(
      List(
        Constant("a"),
        CompoundFunctor(
          "f",

          List(
            Constant("b"),
            Constant("9"),
            Constant("a")
          )
        )
      )
    )

    step.effects should be(
      List(
        Constant("c")
      )
    )
  }



  "Reify" should "work if the provided variables are not all bound to constants but match the action variables" in {
    val action = MainAction(
      "op",
      List(
        Variable("B")
      ),

      List(
        Variable("A"),
        CompoundFunctor(
          "f",

          List(
            Variable("B"),
            Constant("9"),
            Variable("A")
          )
        )
      ),

      List(
        Variable("C")
      )
    )


    val step = action.reify(Map(
      Variable("A") -> Constant("a"),
      Variable("B") -> Variable("X"),
      Variable("C") -> Constant("c")
    ))


    step.action should be(action)

    step.arguments should be(List(
      Variable("X")
    ))

    step.preconditions should be(
      List(
        Constant("a"),
        CompoundFunctor(
          "f",

          List(
            Variable("X"),
            Constant("9"),
            Constant("a")
          )
        )
      )
    )

    step.effects should be(
      List(
        Constant("c")
      )
    )
  }



  "Reify" should "work if the provided variables are not all ground and more than the action variables" in {
    val action = MainAction(
      "op",
      List(
        Variable("B")
      ),

      List(
        Variable("A"),
        CompoundFunctor(
          "f",

          List(
            Variable("B"),
            Constant("9"),
            Variable("A")
          )
        )
      ),

      List(
        Variable("C")
      )
    )


    val step = action.reify(Map(
      Variable("A") -> Constant("a"),
      Variable("B") -> Variable("X"),
      Variable("C") -> Constant("c"),
      Variable("Epsilon") -> Constant("epsilon")
    ))


    step.action should be(action)

    step.arguments should be(List(
      Variable("X")
    ))

    step.preconditions should be(
      List(
        Constant("a"),
        CompoundFunctor(
          "f",

          List(
            Variable("X"),
            Constant("9"),
            Constant("a")
          )
        )
      )
    )

    step.effects should be(
      List(
        Constant("c")
      )
    )
  }


  "Reify" should "support swapping variables" in {
    val action = MainAction(
      "op",
      List(
        Variable("B")
      ),

      List(
        Variable("A"),
        CompoundFunctor(
          "f",

          List(
            Variable("B"),
            Constant("9"),
            Variable("A")
          )
        )
      ),

      List(
        Variable("C")
      )
    )


    val step = action.reify(Map(
      Variable("A") -> Variable("B"),
      Variable("B") -> Variable("A"),
      Variable("C") -> Constant("c")
    ))


    step.action should be(action)

    step.arguments should be(List(
      Variable("A")
    ))


    step.preconditions should be(List(
      Variable("B"),
      CompoundFunctor(
        "f",

        List(
          Variable("A"),
          Constant("9"),
          Variable("B")
        )
      )
    ))


    step.effects should be(List(
      Constant("c")
    ))
  }


  "The string representation" should "coincide with the signature" in {
    val action = MainAction(
      "op",
      List(
        Variable("B")
      ),

      List(
        Variable("A"),
        CompoundFunctor(
          "f",

          List(
            Variable("B"),
            Constant("9"),
            Variable("A")
          )
        )
      ),

      List(
        Variable("C")
      )
    )


    action.toString should be(action.signature)
  }
}
