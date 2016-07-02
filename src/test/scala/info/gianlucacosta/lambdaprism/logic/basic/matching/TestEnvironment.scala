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
import org.scalatest.{FlatSpec, Matchers}

class TestEnvironment extends FlatSpec with Matchers {
  private val environmentACB =
    new Environment ++! Set(
      BindingGroup(
        Set(
          Variable("A"),
          Variable("C")
        ),
        None
      ),
      BindingGroup(
        Set(
          Variable("B")
        ),
        Some(
          Constant("d")
        )
      )
    )


  private val environmentACB_allGround =
    new Environment +!
      BindingGroup(
        Set(
          Variable("A"),
          Variable("B"),
          Variable("C")
        ),
        Some(
          Constant("d")
        )
      )


  private val environmentAC =
    new Environment +!
      BindingGroup(
        Set(
          Variable("A"),
          Variable("C")
        ),
        None
      )


  private val environmentCB =
    new Environment +!
      BindingGroup(
        Set(
          Variable("C"),
          Variable("B")
        ),
        None
      )


  private val environmentB_incompatibleACB =
    new Environment +!
      BindingGroup(
        Set(
          Variable("B")
        ),
        Some(Constant("anything"))
      )


  private val environmentB_compatibleACB =
    new Environment +!
      BindingGroup(
        Set(
          Variable("B")
        ),
        Some(Constant("d"))
      )


  "Variables" should "be correctly listed" in {
    environmentACB.variables should be(Set(
      Variable("A"),
      Variable("B"),
      Variable("C")
    ))
  }


  "Bound variables" should "be retrieved as a map" in {
    environmentACB.boundVariables should be(Map(
      Variable("B") -> Constant("d")
    ))
  }


  "getValue" should "return the value of a bound variable" in {
    environmentACB.getValue(Variable("B")) should be(Some(Constant("d")))
  }


  "getValue" should "return None for a free variable" in {
    environmentACB.getValue(Variable("A")) should be(None)
  }


  "Binding a variable to itself in an environment already having it" should "leave the environment unaltered" in {
    val resultEnvironment =
      environmentACB +! BindingGroup(
        variables = Set(Variable("A")),
        ground = None
      )


    resultEnvironment should be(environmentACB)
  }


  "Adding an existing binding group" should "not alter the environment" in {
    (environmentACB ++ environmentAC) should be(Some(environmentACB))
  }


  "Adding and equality constraint between an unbound variable and a bound variable" should "bind variables" in {
    (environmentACB ++ environmentCB) should be(Some(environmentACB_allGround))
  }


  "Adding a binding group with a variable already bound to a different value" should "return nothing" in {
    (environmentACB ++ environmentB_incompatibleACB) should be(None)
  }


  "Adding a binding group with a variable already bound to the common bound value" should "not alter the environment" in {
    (environmentACB ++ environmentB_compatibleACB) should be(Some(environmentACB))
  }


  "Adding more complex environments" should "work" in {
    val leftEnvironment =
      new Environment ++! Set(
        BindingGroup(
          Set(
            Variable("A"),
            Variable("B")
          ),
          None
        ),
        BindingGroup(
          Set(
            Variable("C")
          ),
          Some(Constant("8"))
        ),
        BindingGroup(
          Set(
            Variable("D")
          ),
          None
        ),
        BindingGroup(
          Set(
            Variable("E")
          ),
          Some(Constant("6"))
        ),
        BindingGroup(
          Set(
            Variable("H")
          ),
          None
        ),
        BindingGroup(
          Set(
            Variable("J")
          ),
          None
        ),
        BindingGroup(
          Set(
            Variable("K")
          ),
          Some(Constant("6"))
        ),
        BindingGroup(
          Set(
            Variable("L")
          ),
          Some(Constant("9"))
        ),
        BindingGroup(
          Set(
            Variable("M")
          ),
          None
        ),
        BindingGroup(
          Set(
            Variable("O")
          ),
          None
        )
      )


    val rightEnvironment =
      new Environment ++! Set(
        BindingGroup(
          Set(
            Variable("A"),
            Variable("D")
          ),
          Some(Constant("3"))
        ),
        BindingGroup(
          Set(
            Variable("C"),
            Variable("H")
          ),
          None
        ),
        BindingGroup(
          Set(
            Variable("E"),
            Variable("J"),
            Variable("K")
          ),
          None
        ),
        BindingGroup(
          Set(
            Variable("L"),
            Variable("M"),
            Variable("N")
          ),
          None
        ),
        BindingGroup(
          Set(
            Variable("P")
          ),
          None
        ),
        BindingGroup(
          Set(
            Variable("Q")
          ),
          Some(Constant("42"))
        )
      )


    val expectedEnvironment =
      new Environment ++! Set(
        BindingGroup(
          Set(
            Variable("A"),
            Variable("B"),
            Variable("D")
          ),
          Some(Constant("3"))
        ),
        BindingGroup(
          Set(
            Variable("C"),
            Variable("H")
          ),
          Some(Constant("8"))
        ),
        BindingGroup(
          Set(
            Variable("E"),
            Variable("J"),
            Variable("K")
          ),
          Some(Constant("6"))
        ),
        BindingGroup(
          Set(
            Variable("H")
          ),
          None
        ),
        BindingGroup(
          Set(
            Variable("L"),
            Variable("M"),
            Variable("N")
          ),
          Some(Constant("9"))
        ),
        BindingGroup(
          Set(
            Variable("O")
          ),
          None
        ),
        BindingGroup(
          Set(
            Variable("P")
          ),
          None
        ),
        BindingGroup(
          Set(
            Variable("Q")
          ),
          Some(Constant("42"))
        )
      )

    leftEnvironment ++ rightEnvironment should be(Some(expectedEnvironment))
  }


  "Compatible environments" should "be detected" in {
    environmentACB.isCompatibleWith(environmentB_compatibleACB) should be(true)
  }


  "Incompatible environments" should "be detected" in {
    environmentACB.isCompatibleWith(environmentB_incompatibleACB) should be(false)
  }
}
