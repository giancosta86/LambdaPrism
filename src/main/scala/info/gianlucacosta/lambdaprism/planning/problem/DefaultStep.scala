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

import info.gianlucacosta.lambdaprism.logic.basic.formulas.{Argument, Literal}

/**
  * Default implementation of Step.
  *
  * Both its preconditions and effects cannot contain contradictions
  *
  * @param action
  * @param arguments
  * @param preconditions
  * @param effects
  */
case class DefaultStep private[problem](
                                         action: Action,
                                         arguments: List[Argument],
                                         preconditions: List[Literal],
                                         effects: List[Literal]
                                       ) extends Step {

  require(
    !preconditions.exists(leftPrecondition =>
      preconditions.exists(rightPrecondition =>
        leftPrecondition == ~rightPrecondition
      )
    ),

    s"There is a contradiction in the preconditions of step ${this}"
  )

  require(
    !effects.exists(leftEffect =>
      effects.exists(rightEffect =>
        leftEffect == ~rightEffect
      )
    ),

    s"There is a contradiction in the effects of step ${this}"
  )
}
