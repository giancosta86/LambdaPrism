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



object Step {
  private def formatSignature(actionName: String, arguments: List[Argument]): String = {
    s"${actionName}${
      if (arguments.nonEmpty)
        arguments.mkString(
          "(",
          ", ",
          ")"
        )
      else
        ""
    }"
  }
}


/**
  * Reification of an Action: multiple different steps can be obtained by
  * reifying the very same Action
  */
trait Step {
  /**
    * The action from which the step was created
    *
    * @return
    */
  def action: Action

  /**
    * The actual arguments - each argument can be either a variable or a constant
    *
    * @return
    */
  def arguments: List[Argument]


  def preconditions: List[Literal]

  def effects: List[Literal]

  /**
    * Positive effects
    */
  lazy val addedEffects: List[Literal] =
    effects
      .filter(_.isPositive)


  /**
    * Negative effects
    */
  lazy val deletedEffects: List[Literal] =
    effects
      .filter(!_.isPositive)
      .map(~_)


  /**
    * Signature - including the action name and the actual step arguments, if any
    */
  lazy val signature: String =
    Step.formatSignature(
      action.name,
      arguments
    )


  /**
    * Returns a signature whose variables have been replaced by the given arguments
    *
    * @param replacements A Map[Variable, Argument] whose variables will be replaced within the
    *                     signature. The map can also contain variables not in the signature,
    *                     as well as only a subset of the signature variables
    *
    * @return The signature, after replacing the given variables
    */
  def replaceVariablesInSignature(replacements: Map[Variable, Argument]): String =
    Step.formatSignature(
      action.name,

      arguments
        .map(
          _.replaceVariables(replacements)
        )
    )


  override def toString: String =
    signature
}
