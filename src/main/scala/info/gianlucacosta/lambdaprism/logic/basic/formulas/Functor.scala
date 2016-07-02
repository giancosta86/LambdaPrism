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

import java.util.regex.Pattern


object Functor {
  val ValidNamePattern = Pattern.compile(raw"\b[a-z0-9][A-Za-z0-9_]*\b")
}


/**
  * Logic functor, having arbitrary arity
  */
trait Functor extends Term with Ordered[Functor] {
  /**
    * The functor's name
    */
  def name: String

  /**
    * The arguments passed to the functor
    *
    * @return
    */
  def arguments: List[Argument]


  /**
    * The number of arguments
    */
  def arity: Int =
    arguments.size


  override def replaceVariable(variable: Variable, argument: Argument): Functor


  override def compare(that: Functor): Int = {
    val nameResult = name.compareTo(that.name)

    if (nameResult != 0)
      nameResult
    else {
      val arityResult = arity.compareTo(that.arity)

      arityResult
    }
  }
}
