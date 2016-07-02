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

import info.gianlucacosta.lambdaprism.logic.basic.formulas.{Argument, Literal, Variable}

/**
  * NOP action - that is, an action whose only effect is its precondition
  *
  * @param precondition
  */
case class NopAction(precondition: Literal) extends Action {
  override val name: String =
    s"NOP {${precondition}}"


  override val parameters: List[Variable] =
    List()

  override val preconditions: List[Literal] =
    List(precondition)

  override val effects: List[Literal] =
    List(precondition)


  override def toLanguageString: String =
    throw new UnsupportedOperationException

  override val variables: Set[Variable] =
    precondition.variables


  override def reify(argumentMap: Map[Variable, Argument]): NopStep = {
    require(variables.subsetOf(argumentMap.keySet))

    NopStep(
      this,
      precondition.replaceVariables(argumentMap)
    )
  }
}
