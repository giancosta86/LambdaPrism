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

import info.gianlucacosta.lambdaprism.logic.basic.formulas._


object SymbolicMatch {
  /**
    * Applies the unification algorithm to the provided pair of literals
    *
    * @param left
    * @param right
    * @return Some(symbolic match) if unification succeeded, None otherwise
    */
  def unify(left: Literal, right: Literal): Option[SymbolicMatch] =
    if (left.isPositive != right.isPositive)
      None
    else {
      val unificationEnvironmentOption =
        unify(left.term, right.term, new Environment)

      unificationEnvironmentOption.map(unificationEnvironment =>
        SymbolicMatch(left, right, unificationEnvironment)
      )
    }


  private def unify(left: Term, right: Term, environment: Environment):
  Option[Environment] = {
    left match {
      case leftCompoundFunctor: CompoundFunctor =>
        right match {
          case rightCompoundFunctor: CompoundFunctor =>
            unifyCompoundFunctors(leftCompoundFunctor, rightCompoundFunctor, environment)

          case rightVariable: Variable =>
            None

          case rightConstant: Constant =>
            None
        }


      case leftVariable: Variable =>
        right match {
          case rightCompoundFunctor: CompoundFunctor =>
            None

          case rightVariable: Variable =>
            unifyVariables(leftVariable, rightVariable, environment)

          case rightConstant: Constant =>
            unifyVariableAndConstant(leftVariable, rightConstant, environment)
        }


      case leftConstant: Constant =>
        right match {
          case rightCompoundFunctor: CompoundFunctor =>
            None

          case rightVariable: Variable =>
            unifyVariableAndConstant(rightVariable, leftConstant, environment)

          case rightConstant: Constant =>
            unifyConstants(leftConstant, rightConstant, environment)
        }
    }
  }


  private def unifyCompoundFunctors(
                                     leftFunctor: CompoundFunctor,
                                     rightFunctor: CompoundFunctor,
                                     environment: Environment
                                   )
  : Option[Environment] =
    if (leftFunctor.name != rightFunctor.name)
      None
    else
      unifyArguments(leftFunctor.arguments, rightFunctor.arguments, environment)


  private def unifyArguments(leftArguments: List[Argument], rightArguments: List[Argument], environment: Environment)
  : Option[Environment] =
    if (leftArguments.length != rightArguments.length)
      None
    else if (leftArguments.isEmpty)
      Some(environment)
    else {
      val leftArgument = leftArguments.head
      val rightArgument = rightArguments.head

      val newEnvironmentOption = unify(leftArgument, rightArgument, environment)

      newEnvironmentOption.flatMap(newEnvironment =>
        unifyArguments(
          leftArguments.tail,
          rightArguments.tail,
          newEnvironment
        )
      )
    }


  private def unifyVariableAndConstant(
                                        variable: Variable,
                                        constant: Constant,
                                        environment: Environment
                                      ):
  Option[Environment] =
    environment + BindingGroup(
      variables = Set(variable),
      ground = Some(constant)
    )


  private def unifyVariables(
                              leftVariable: Variable,
                              rightVariable: Variable,
                              environment: Environment
                            )
  : Option[Environment] =
    environment + BindingGroup(
      variables = Set(leftVariable, rightVariable),
      ground = None
    )


  private def unifyConstants(
                              leftConstant: Constant,
                              rightConstant: Constant,
                              environment: Environment
                            )
  : Option[Environment] =
    if (leftConstant == rightConstant)
      Some(environment)
    else
      None
}


/**
  * Symbolic match - obtained via SymbolicMatch.unify()
  *
  * @param left        The first literal
  * @param right       The other literal
  * @param environment The environment containing the match constraints
  */
case class SymbolicMatch private(
                                  left: Literal,
                                  right: Literal,
                                  environment: Environment
                                ) {
  override def toString: String =
    s"${left} --> ${right}"
}