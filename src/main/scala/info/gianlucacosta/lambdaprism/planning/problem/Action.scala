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
  * Generic action archetype in a planning problem.
  *
  * An action can <b>not</b> belong to a concrete plan: you'll need to
  * <i>reify</i> it to create a Step, which can be added to a plan
  */
trait Action {
  /**
    * The action name
    *
    * @return
    */
  def name: String

  /**
    * Variables to be passed to the action
    *
    * @return
    */
  def parameters: List[Variable]

  /**
    * A list of literals required to perform the action
    *
    * @return
    */
  def preconditions: List[Literal]

  /**
    * A list of effects caused by the execution of the action
    *
    * @return
    */
  def effects: List[Literal]

  /**
    * All the variables belonging to an action
    *
    * @return
    */
  def variables: Set[Variable]


  /**
    * Instantiates a step for the given action
    *
    * @param argumentMap A map Variable->Argument that must include
    *                    at least all the variables of the action.
    *                    Every Argument can be a Variable or a Constant,
    *                    and swapping variables is allowed - as replacements
    *                    occur simultaneously
    * @return
    */
  def reify(argumentMap: Map[Variable, Argument] = Map()): Step = {
    require(variables.subsetOf(argumentMap.keySet))

    DefaultStep(
      action =
        this,

      arguments =
        parameters.map(parameter => argumentMap(parameter)),

      preconditions =
        preconditions
          .map(_.replaceVariables(argumentMap))
          .distinct,

      effects =
        effects
          .map(_.replaceVariables(argumentMap))
          .distinct
    )
  }


  /**
    * The action's signature - including its name and, if present, its parameters
    *
    * @return
    */
  def signature =
    s"${name}${if (parameters.nonEmpty) parameters.mkString("(", ", ", ")") else ""}"


  override def toString: String =
    signature


  /**
    * Returns the action in the planning problem description language
    *
    * @return
    */
  def toLanguageString: String
}
