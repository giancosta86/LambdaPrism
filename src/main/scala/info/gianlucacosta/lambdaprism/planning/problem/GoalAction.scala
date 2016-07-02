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

import info.gianlucacosta.lambdaprism.logic.basic.formulas.Literal

case object GoalAction {
  val Name: String = "goal"
}

/**
  * Action describing the goal of a planning problem
  *
  * @param goalLiterals
  */
case class GoalAction(goalLiterals: List[Literal]) extends Action {
  override val name = GoalAction.Name
  override val parameters = List()
  override val preconditions = goalLiterals
  override val effects = List()
  override val variables = goalLiterals.flatMap(_.variables).toSet

  override def toLanguageString: String =
    s"${ProblemLanguage.GoalKeyword}: ${goalLiterals.mkString(", ")}"
}
