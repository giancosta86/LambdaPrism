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

package info.gianlucacosta.lambdaprism.planning.problem.parser

import info.gianlucacosta.lambdaprism.logic.basic.formulas._
import info.gianlucacosta.lambdaprism.planning.problem.parser.PlanningProblemParser._
import info.gianlucacosta.lambdaprism.planning.problem.{MainAction, Problem}

import scala.collection.JavaConversions._

private class ProblemBuildingVisitor extends PlanningProblemBaseVisitor[Object] {
  override def visitProblem(ctx: ProblemContext): Problem = {
    val startAction = visitStartDeclaration(ctx.startDeclaration())
    val goalAction = visitGoalDeclaration(ctx.goalDeclaration())


    val mainActions =
      if (ctx.mainAction() != null)
        ctx.mainAction().map(visitMainAction).toList
      else
        List()

    Problem(
      startAction,
      goalAction,
      mainActions
    )
  }


  override def visitStartDeclaration(ctx: StartDeclarationContext): List[Literal] = {
    ctx.literals().literal().map(visitLiteral).toList
  }


  override def visitGoalDeclaration(ctx: GoalDeclarationContext): List[Literal] = {
    ctx.literals().literal().map(visitLiteral).toList
  }


  override def visitMainAction(ctx: MainActionContext): MainAction = {
    val name = ctx.signature().FUNCTOR_NAME().getText


    val parameters =
      if (ctx.signature().parameters() != null)
        ctx.signature().parameters().variable().map(visitVariable).toList
      else
        List()


    val preconditions =
      if (ctx.preconditions() != null)
        ctx.preconditions().literals().literal().map(visitLiteral).toList
      else
        List()


    val effects =
      if (ctx.effects() != null)
        ctx.effects().literals().literal().map(visitLiteral).toList
      else
        List()

    new MainAction(
      name,
      parameters,
      preconditions,
      effects
    )
  }


  override def visitLiteral(ctx: LiteralContext): Literal = {
    val isPositive = ctx.NOT() == null

    val functor =
      visitFunctor(ctx.functor())


    if (isPositive)
      functor
    else
      ~functor
  }


  override def visitFunctor(ctx: FunctorContext): Functor = {
    val name = ctx.FUNCTOR_NAME().getText

    if (ctx.arguments() == null)
      Constant(name)
    else {
      val arguments = ctx.arguments().argument().map(visitArgument).toList

      CompoundFunctor(name, arguments)
    }
  }


  override def visitArgument(ctx: ArgumentContext): Argument = {
    if (ctx.constant() != null)
      visitConstant(ctx.constant())
    else if (ctx.variable() != null)
      return visitVariable(ctx.variable())
    else
      throw new AssertionError("Unknown argument kind")
  }


  override def visitConstant(ctx: ConstantContext): Constant = {
    Constant(ctx.FUNCTOR_NAME().getText)
  }


  override def visitVariable(ctx: VariableContext): Variable = {
    Variable(ctx.VARIABLE_NAME().getText)
  }
}
