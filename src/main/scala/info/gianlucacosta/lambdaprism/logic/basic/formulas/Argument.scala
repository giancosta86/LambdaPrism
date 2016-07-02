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

object Argument {
  /**
    * Parses the given string and returns a suitable instance of Argument
    *
    * @param argumentToken
    * @return
    */
  def parse(argumentToken: String): Argument = {
    require(
      Option(argumentToken).exists(_.nonEmpty),
      "Argument token not provided"
    )

    if (Functor.ValidNamePattern.matcher(argumentToken).matches()) {
      Constant(argumentToken)
    } else if (Variable.ValidNamePattern.matcher(argumentToken).matches()) {
      Variable(argumentToken)
    } else {
      throw new IllegalArgumentException(s"Invalid token: '${argumentToken}'")
    }
  }
}


/**
  * Argument of a functor: in this simplified model, it can only be a constant or a variable.
  */
trait Argument extends Term {
  override def replaceVariable(variable: Variable, argument: Argument): Argument
}