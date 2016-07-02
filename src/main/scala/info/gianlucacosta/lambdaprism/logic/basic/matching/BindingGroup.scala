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

package info.gianlucacosta.lambdaprism.logic.basic.matching

import info.gianlucacosta.lambdaprism.logic.basic.formulas.{Constant, Variable}

/**
  * A binding group consists of:
  *
  * <ul>
  * <li>
  * A set of variables, constrained to have the very same value, which could be undefined
  * </li>
  *
  * <li>
  * The ground value assigned to every variable: Some(Constant) or None (if it is undefined)
  * </li>
  * </ul>
  *
  * @param variables
  * @param ground
  */
case class BindingGroup(variables: Set[Variable], ground: Option[Constant]) {
  require(variables.nonEmpty)

  lazy val sortedVariables =
    variables.toList.sorted


  def contains(variable: Variable): Boolean =
    variables.contains(variable)


  override def toString: String =
    sortedVariables.mkString(" = ") +
      ground.map(actualGround => s" = ${actualGround}").getOrElse("")
}
