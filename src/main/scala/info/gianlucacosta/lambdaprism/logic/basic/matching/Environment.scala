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
  * Managed set of binding groups.
  *
  * More precisely, an Environment starts empty, with no binding groups, and can
  * be populated using its operators, whose documentation is reported below.
  *
  * @param bindingGroups
  */
case class Environment private(bindingGroups: Set[BindingGroup]) {
  def this() =
    this(Set())


  /**
    * Adds a binding group to the environment, with the following possible outcomes:
    *
    * <ul>
    * <li>
    * If the new binding group introduces a constraint compatible with the current binding groups,
    * a new environment is returned. <i>It is ensured that the binding groups in the new environment constitute
    * the smallest set of binding groups satisfying the constraints</i>
    * </li>
    *
    * <li>
    * None, if the new binding group violates the constraints of the current binding groups
    * </li>
    * </ul>
    *
    * @param newBindingGroup
    * @return Some(new_environment) or None
    */
  def +(newBindingGroup: BindingGroup): Option[Environment] = {
    val (affectedGroups, unaffectedGroups) = bindingGroups.partition(bindingGroup =>
      bindingGroup.variables.intersect(newBindingGroup.variables).nonEmpty
    )

    val mergingGroups =
      Set(newBindingGroup) ++ affectedGroups


    val groundsInMergingGroups =
      mergingGroups
        .map(_.ground)
        .filter(_.nonEmpty)


    groundsInMergingGroups.toList match {
      case List(_, _, _*) =>
        None

      case List(sharedGround) =>
        val mergedGroup =
          BindingGroup(
            variables = mergingGroups.flatMap(_.variables),
            ground = sharedGround
          )

        Some(
          Environment(
            unaffectedGroups + mergedGroup
          )
        )


      case List() =>
        val mergedGroup =
          BindingGroup(
            variables = mergingGroups.flatMap(_.variables),
            ground = None
          )

        Some(
          Environment(
            unaffectedGroups + mergedGroup
          )
        )
    }
  }


  /**
    * Syntactic sugar to simplify the creation of an environment: it always
    * returns an environment, raising an exception if the "+" operation fails
    *
    * @param newBindingGroup
    * @return
    */
  def +!(newBindingGroup: BindingGroup): Environment =
    (this + newBindingGroup).get


  /**
    * The variables declared in the environment
    */
  lazy val variables =
    bindingGroups.flatMap(_.variables)


  /**
    * A map containing just the <b>bound</b> variables in the environment
    */
  lazy val boundVariables: Map[Variable, Constant] =
    bindingGroups
      .filter(_.ground.nonEmpty)
      .flatMap(bindingGroup =>
        bindingGroup.variables.map(variable =>
          variable -> bindingGroup.ground.get
        )
      )
      .toMap


  /**
    * Returns the constant value of the given value, if it belongs to the environment and is bound. Otherwise, it returns None
    *
    * @param variable
    * @return
    */
  def getValue(variable: Variable): Option[Constant] =
    boundVariables.get(variable)


  /**
    * Adds all the binding groups in the set to the environment.
    *
    * @param otherBindingGroups
    * @return Some(new_environment) if the result was feasible, None otherwise
    */
  def ++(otherBindingGroups: Set[BindingGroup]): Option[Environment] =
    if (otherBindingGroups.isEmpty)
      Some(this)
    else {
      val partialResultOption =
        this + otherBindingGroups.head

      partialResultOption.flatMap(partialResult =>
        partialResult ++ otherBindingGroups.tail
      )
    }


  /**
    * Adds all the binding groups, returning the new environment and throwing
    * an exception if the addition would violate some constraint
    *
    * @param otherBindingGroups
    * @return
    */
  def ++!(otherBindingGroups: Set[BindingGroup]): Environment =
    (this ++ otherBindingGroups).get


  /**
    * Adds all the binding groups belonging to the given environment
    *
    * @param other
    * @return
    */
  def ++(other: Environment): Option[Environment] =
    this ++ other.bindingGroups


  /**
    * Returns true if the environment is compatible with the given environment - that is, if a new environment
    * can be obtained by merging their binding groups
    *
    * @param environment
    * @return
    */
  def isCompatibleWith(environment: Environment): Boolean =
    (this ++ environment).nonEmpty


  override def toString: String =
    bindingGroups.mkString("\n")
}
