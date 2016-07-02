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
  * Logic constant - which is a functor having no arguments.
  *
  * @param name
  */
case class Constant(name: String) extends Functor with Argument {
  require(
    Option(name).exists(_.nonEmpty),
    "Constant name not provided"
  )

  require(
    Functor.ValidNamePattern.matcher(name).matches(),
    s"Invalid constant name: '${name}'"
  )


  override val arguments: List[Argument] =
    List()


  override val arity: Int =
    0


  override def variables: Set[Variable] =
    Set()


  override def replaceVariable(variable: Variable, argument: Argument): Constant =
    this


  override def isGround: Boolean =
    true


  override def toString: String =
    name
}
