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


class TestNopAction extends FlatSpec with Matchers {
  val nopActionPrecondition =
    CompoundFunctor(
      "f",
      List(
        Variable("A")
      )
    )


  val nopStepPrecondition =
    CompoundFunctor(
      "f",
      List(
        Constant("a")
      )
    )


  val positiveNopAction = NopAction(
    nopActionPrecondition
  )


  val negativeNopAction = NopAction(
    ~nopActionPrecondition
  )


  "A positive NOP action" should "always be named according to its precondition'" in {
    positiveNopAction.name should be("NOP {f(A)}")
  }


  "A positive NOP action" should "have no parameters" in {
    positiveNopAction.parameters should be(empty)
  }

  "A positive NOP action" should "have the variables of its precondition" in {
    positiveNopAction.variables should be(Set(
      Variable("A")
    )
    )
  }


  "A positive NOP action" should "have only a precondition" in {
    positiveNopAction.preconditions should be(List(nopActionPrecondition))
  }


  "A positive NOP action" should "have only an effect, equal to its precondition" in {
    positiveNopAction.effects should be(List(nopActionPrecondition))
  }


  "A positive NOP action" should "have a dedicated string representation" in {
    positiveNopAction.toString should be("NOP {f(A)}")
  }


  "A negative NOP action" should "always be named according to its precondition" in {
    negativeNopAction.name should be("NOP {~f(A)}")
  }


  "A negative NOP action" should "have no parameters" in {
    negativeNopAction.parameters should be(empty)
  }


  "A negative NOP action" should "have the variables of its precondition" in {
    negativeNopAction.variables should be(Set(
      Variable("A")
    )
    )
  }


  "A negative NOP action" should "have only a precondition" in {
    negativeNopAction.preconditions should be(List(~nopActionPrecondition))
  }


  "A negative NOP action" should "have only an effect, equal to its precondition" in {
    negativeNopAction.effects should be(List(~nopActionPrecondition))
  }


  "A negative NOP action" should "have a dedicated string representation" in {
    negativeNopAction.toString should be("NOP {~f(A)}")
  }


  "NOP actions" should "NOT provide their description in the problem language" in {
    intercept[UnsupportedOperationException] {
      positiveNopAction.toLanguageString
    }
  }



  "Reifying a positive NOP action" should "correctly create a NOP step" in {
    val nopStep = positiveNopAction.reify(
      Map(
        Variable("A") -> Constant("a")
      )
    )


    nopStep.action should be(positiveNopAction)
    nopStep.arguments should be(empty)
    nopStep.preconditions should be(List(nopStepPrecondition))
    nopStep.effects should be(List(nopStepPrecondition))
  }



  "Reifying a negative NOP action" should "correctly create a NOP step" in {
    val nopStep = negativeNopAction.reify(
      Map(
        Variable("A") -> Constant("a")
      )
    )


    nopStep.action should be(negativeNopAction)
    nopStep.arguments should be(empty)
    nopStep.preconditions should be(List(~nopStepPrecondition))
    nopStep.effects should be(List(~nopStepPrecondition))
  }
}
