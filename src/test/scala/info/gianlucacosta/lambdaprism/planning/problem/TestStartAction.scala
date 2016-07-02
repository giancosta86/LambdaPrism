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

class TestStartAction extends FlatSpec with Matchers {
  val startLiterals = List(
    Constant("b"),
    CompoundFunctor(
      "f",

      List(
        Constant("c"),
        Variable("A"),
        Constant("d")
      )
    )
  )

  val startAction =
    StartAction(startLiterals)


  "Name" should "be a constant" in {
    startAction.name should be(StartAction.Name)
  }


  "Parameters" should "be empty" in {
    startAction.parameters should be(empty)
  }


  "Preconditions" should "be empty" in {
    startAction.preconditions should be(empty)
  }


  "Effects" should "be the start literals" in {
    startAction.effects should be(startLiterals)
  }


  "Variables" should "include all the variables of the start literals" in {
    startAction.variables should be(Set(
      Variable("A")
    ))
  }


  "Reify" should "work" in {
    val groundLiterals = List(
      Constant("b"),
      CompoundFunctor(
        "f",

        List(
          Constant("c"),
          Constant("a"),
          Constant("d")
        )
      )
    )


    val startStep = startAction.reify(Map(
      Variable("A") -> Constant("a")
    ))


    startStep.action should be(startAction)

    startStep.arguments should be(empty)

    startStep.preconditions should be(empty)

    startStep.effects should be(groundLiterals)

    startStep.addedEffects should be(groundLiterals)

    startStep.deletedEffects should be(empty)
  }
}
