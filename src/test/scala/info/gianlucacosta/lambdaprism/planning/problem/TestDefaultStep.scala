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

import info.gianlucacosta.lambdaprism.logic.basic.formulas.{Argument, CompoundFunctor, Constant, Variable}
import org.scalatest.{FlatSpec, Matchers}


class TestDefaultStep extends FlatSpec with Matchers {
  val action =
    MainAction(
      "defaultOp",

      List(
        Variable("A"),
        Variable("B")
      ),

      List(
        Constant("a")
      ),


      List(
        CompoundFunctor(
          "f",
          List(
            Variable("A")
          )
        ),

        ~Constant("c"),

        ~CompoundFunctor(
          "g",
          List(
            Variable("C")
          )
        ),

        Constant("d"),
        CompoundFunctor(
          "h",
          List(
            Variable("A"),
            Variable("B")
          )
        )
      )
    )


  val step =
    action.reify(Map(
      Variable("A") -> Constant("epsilon"),
      Variable("B") -> Variable("X"),
      Variable("C") -> Constant("sigma")
    ))


  val contradictionAction =
    MainAction(
      "contradictionOp",

      List(),

      List(
        Variable("A"),
        ~Constant("a")
      ),

      List(
        ~Variable("B"),
        Constant("b")
      )
    )

  val potentialDuplicationAction =
    MainAction(
      "duplicationOp",

      List(),

      List(
        Variable("A"),
        Constant("a")
      ),

      List(
        ~Variable("B"),
        ~Constant("b")
      )
    )


  "Added effects" should "be the positive effects" in {
    step.addedEffects should be(List(
      CompoundFunctor(
        "f",
        List(
          Constant("epsilon")
        )
      ),

      Constant("d"),
      CompoundFunctor(
        "h",
        List(
          Constant("epsilon"),
          Variable("X")
        )
      )
    ))
  }


  "Deleted effects" should "be the negative effects, but listed as positive" in {
    step.deletedEffects should be(List(
      Constant("c"),
      CompoundFunctor(
        "g",
        List(
          Constant("sigma")
        )
      )
    ))
  }


  "Signature" should "include the actual arguments" in {
    step.signature should be("defaultOp(epsilon, X)")
  }


  "Signature of a step deriving from a parameterless action" should "include just the action name" in {
    val parameterlessAction =
      MainAction(
        "op",
        List(),
        List(),
        List()
      )


    val parameterlessStep =
      parameterlessAction.reify()

    parameterlessStep.signature should be("op")
  }


  "Replacing variables in the signature of a parameterless action" should "return only the action name" in {
    val parameterlessAction =
      MainAction(
        "op",
        List(),
        List(),
        List()
      )


    val parameterlessStep =
      parameterlessAction.reify()

    parameterlessStep.replaceVariablesInSignature(
      Map[Variable, Argument]()
    ) should be("op")
  }


  "Replacing variables in a signature" should "work correctly also when passing variables not in the signature" in {
    step.replaceVariablesInSignature(
      Map(
        Variable("A") -> Constant("a"),
        Variable("X") -> Constant("test"),
        Variable("Z") -> Constant("z")
      )
    ) should be("defaultOp(epsilon, test)")
  }



  "Reifying a step" should "NOT cause contradictions in the preconditions" in {
    intercept[IllegalArgumentException] {
      contradictionAction.reify(Map(
        Variable("A") -> Constant("a"),
        Variable("B") -> Constant("x")
      ))
    }
  }


  "Reifying a step" should "NOT cause contradictions in the effects" in {
    intercept[IllegalArgumentException] {
      contradictionAction.reify(Map(
        Variable("A") -> Constant("x"),
        Variable("B") -> Constant("b")
      ))
    }
  }


  "Reifying a step" should "ensure there are no duplicated preconditions" in {
    val noduplicationStep =
      potentialDuplicationAction.reify(Map(
        Variable("A") -> Constant("a"),
        Variable("B") -> Constant("b")
      ))

    noduplicationStep.preconditions should be(List(Constant("a")))
  }


  "Reifying a step" should "ensure there are no duplicated effects" in {
    val noDuplicationStep =
      potentialDuplicationAction.reify(Map(
        Variable("A") -> Constant("a"),
        Variable("B") -> Constant("b")
      ))

    noDuplicationStep.effects should be(List(~Constant("b")))
    noDuplicationStep.deletedEffects should be(List(Constant("b")))
  }
}
