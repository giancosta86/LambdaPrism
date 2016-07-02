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
  * Step created by reifying a NOP action
  *
  * @param action
  * @param precondition
  */
case class NopStep private[problem](action: NopAction, precondition: Literal) extends Step {
  override val arguments: List[Argument] =
    List()

  override val preconditions: List[Literal] =
    List(precondition)

  override val effects: List[Literal] =
    List(precondition)

  override def toString: String =
    s"NOP {${precondition}}"
}
