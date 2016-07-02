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
  * Logic literal
  *
  */
trait Literal extends Formula {
  def unary_~ : Literal

  /**
    * The set of variables appearing in the literal
    *
    * @return
    */
  def variables: Set[Variable]

  /**
    * Tests if the literal is ground (variable-free)
    *
    * @return
    */
  def isGround: Boolean


  /**
    * True if the literal is positive
    *
    * @return
    */
  def isPositive: Boolean


  /**
    * Replaces multiple variables <b>at the same time</b> (therefore, variables can be <b>swapped</b>)
    *
    * @param replacements
    * @return
    */
  def replaceVariables(replacements: Map[Variable, Argument]): Literal


  /**
    * The underlying term
    *
    * @return
    */
  def term: Term
}
