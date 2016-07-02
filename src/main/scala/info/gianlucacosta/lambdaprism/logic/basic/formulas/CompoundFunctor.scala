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

package info.gianlucacosta.lambdaprism.logic.basic.formulas

/**
  * Functor having arity >= 1.
  *
  * @param name
  * @param arguments
  */
case class CompoundFunctor(name: String, arguments: List[Argument]) extends Functor {
  require(
    Option(name).exists(_.nonEmpty),
    "Compound functor name not provided"
  )

  require(
    Functor.ValidNamePattern.matcher(name).matches(),
    s"Invalid compound functor name: '${name}'"
  )

  require(
    arguments.nonEmpty,
    "A compound functor must have arity >= 1. Create a Constant instead"
  )

  override val arity: Int =
    arguments.size


  override def variables: Set[Variable] =
    arguments.flatMap(_.variables).toSet


  override def replaceVariable(variable: Variable, argument: Argument): CompoundFunctor =
    copy(
      arguments = arguments.map(_.replaceVariable(variable, argument))
    )


  override def isGround: Boolean =
    arguments.forall(_.isGround)


  override def toString: String =
    s"${name}${arguments.mkString("(", ", ", ")")}"
}
