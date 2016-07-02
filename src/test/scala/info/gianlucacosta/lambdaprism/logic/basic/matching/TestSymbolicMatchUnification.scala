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

import info.gianlucacosta.lambdaprism.logic.basic.formulas.{CompoundFunctor, Constant, Literal, Variable}
import org.scalatest.{FlatSpec, Matchers}

class TestSymbolicMatchUnification extends FlatSpec with Matchers {
  "a and ~a" should "NOT match" in {
    val left =
      Constant("a")

    val right =
      ~left

    val resultMatchOption =
      SymbolicMatch.unify(left, right)

    resultMatchOption should be(None)
  }


  "a and a" should "match" in {
    val left =
      Constant("a")

    val right =
      Constant("a")

    val expectedBindingGroups =
      Set[BindingGroup]()

    val resultMatchOption =
      SymbolicMatch.unify(left, right)

    assertMatching(resultMatchOption, left, right, expectedBindingGroups)
  }


  "a and b" should "NOT match" in {
    val left =
      Constant("a")

    val right =
      Constant("b")

    val resultMatchOption = SymbolicMatch.unify(left, right)

    resultMatchOption should be(None)
  }



  "X and a" should "match" in {
    val left =
      Variable("X")


    val right =
      Constant("a")


    val expectedBindingGroups =
      Set(
        BindingGroup(
          Set(Variable("X")),
          Some(Constant("a"))
        )
      )

    val resultMatchOption = SymbolicMatch.unify(left, right)

    assertMatching(resultMatchOption, left, right, expectedBindingGroups)
  }


  "a and X" should "match" in {
    val left =
      Constant("a")


    val right =
      Variable("X")

    val expectedBindingGroups =
      Set(
        BindingGroup(
          Set(Variable("X")),
          Some(Constant("a"))
        )
      )

    val resultMatchOption = SymbolicMatch.unify(left, right)

    assertMatching(resultMatchOption, left, right, expectedBindingGroups)
  }


  "A and A" should "match" in {
    val left =
      Variable("A")


    val right =
      Variable("A")

    val expectedBindingGroups =
      Set(
        BindingGroup(
          Set(Variable("A")),
          None
        )
      )

    val resultMatchOption = SymbolicMatch.unify(left, right)

    assertMatching(resultMatchOption, left, right, expectedBindingGroups)
  }


  "A and B" should "match" in {
    val left =
      Variable("A")


    val right =
      Variable("B")


    val expectedBindingGroups =
      Set(
        BindingGroup(
          Set(Variable("A"), Variable("B")),
          None
        )
      )

    val resultMatchOption = SymbolicMatch.unify(left, right)

    assertMatching(resultMatchOption, left, right, expectedBindingGroups)
  }


  "a and f(a)" should "NOT match" in {
    val left =
      Constant("a")


    val right =
      CompoundFunctor(
        "f",
        List(
          Constant("a")
        )
      )

    SymbolicMatch.unify(left, right) should be(None)
  }


  "A and f(A)" should "NOT match" in {
    val left =
      Variable("A")


    val right =
      CompoundFunctor(
        "f",
        List(
          Variable("A")
        )
      )

    SymbolicMatch.unify(left, right) should be(None)
  }


  "A and f(a)" should "NOT match" in {
    val left =
      Variable("A")


    val right =
      CompoundFunctor(
        "f",
        List(
          Constant("a")
        )
      )

    SymbolicMatch.unify(left, right) should be(None)
  }


  "f(a) and g(a)" should "NOT match" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Constant("a")
        )
      )


    val right =
      CompoundFunctor(
        "g",
        List(
          Constant("a")
        )
      )

    SymbolicMatch.unify(left, right) should be(None)
  }


  "f(a) and f(a)" should "match" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Constant("a")
        )
      )


    val right =
      CompoundFunctor(
        "f",
        List(
          Constant("a")
        )
      )

    val expectedBindingGroups =
      Set[BindingGroup]()

    val resultMatchOption = SymbolicMatch.unify(left, right)

    assertMatching(resultMatchOption, left, right, expectedBindingGroups)
  }


  "f(A) and f(a)" should "match" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Variable("A")
        )
      )


    val right =
      CompoundFunctor(
        "f",
        List(
          Constant("a")
        )
      )

    val expectedBindingGroups =
      Set(
        BindingGroup(
          Set(Variable("A")),
          Some(Constant("a"))
        )
      )

    val resultMatchOption = SymbolicMatch.unify(left, right)

    assertMatching(resultMatchOption, left, right, expectedBindingGroups)
  }


  "f(A, A) and f(2, 2)" should "match" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Variable("A"),
          Variable("A")
        )
      )


    val right =
      CompoundFunctor(
        "f",
        List(
          Constant("2"),
          Constant("2")
        )
      )

    val expectedBindingGroups =
      Set(
        BindingGroup(
          Set(Variable("A")),
          Some(Constant("2"))
        )
      )

    val resultMatchOption = SymbolicMatch.unify(left, right)

    assertMatching(resultMatchOption, left, right, expectedBindingGroups)
  }


  "f(A, A) and f(2, 3)" should "NOT match" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Variable("A"),
          Variable("A")
        )
      )


    val right =
      CompoundFunctor(
        "f",
        List(
          Constant("2"),
          Constant("3")
        )
      )

    val resultMatchOption = SymbolicMatch.unify(left, right)

    resultMatchOption should be(None)
  }



  "f(A, A) and f(2)" should "NOT match" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Variable("A"),
          Variable("A")
        )
      )


    val right =
      CompoundFunctor(
        "f",
        List(
          Constant("2")
        )
      )

    val resultMatchOption = SymbolicMatch.unify(left, right)

    resultMatchOption should be(None)
  }



  "f(A, 4) and f(4, A)" should "match" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Variable("A"),
          Constant("4")
        )
      )


    val right =
      CompoundFunctor(
        "f",
        List(
          Constant("4"),
          Variable("A")
        )
      )

    val expectedBindingGroups =
      Set(
        BindingGroup(
          Set(Variable("A")),
          Some(Constant("4"))
        )
      )

    val resultMatchOption = SymbolicMatch.unify(left, right)

    assertMatching(resultMatchOption, left, right, expectedBindingGroups)
  }


  "f(3, A) and f(B, 2)" should "match" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Constant("3"),
          Variable("A")
        )
      )


    val right =
      CompoundFunctor(
        "f",
        List(
          Variable("B"),
          Constant("2")
        )
      )

    val expectedBindingGroups =
      Set(
        BindingGroup(
          Set(Variable("A")),
          Some(Constant("2"))
        ),

        BindingGroup(
          Set(Variable("B")),
          Some(Constant("3"))
        )
      )

    val resultMatchOption = SymbolicMatch.unify(left, right)

    assertMatching(resultMatchOption, left, right, expectedBindingGroups)
  }


  "f(3, A) and f(A, B)" should "match" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Constant("3"),
          Variable("A")
        )
      )


    val right =
      CompoundFunctor(
        "f",
        List(
          Variable("A"),
          Variable("B")
        )
      )

    val expectedBindingGroups =
      Set(
        BindingGroup(
          Set(Variable("A"), Variable("B")),
          Some(Constant("3"))
        )
      )

    val resultMatchOption = SymbolicMatch.unify(left, right)

    assertMatching(resultMatchOption, left, right, expectedBindingGroups)
  }



  "f(3, A, 4) and f(B, C, C)" should "match" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Constant("3"),
          Variable("A"),
          Constant("4")
        )
      )


    val right =
      CompoundFunctor(
        "f",
        List(
          Variable("B"),
          Variable("C"),
          Variable("C")
        )
      )

    val expectedBindingGroups =
      Set(
        BindingGroup(
          Set(Variable("A"), Variable("C")),
          Some(Constant("4"))
        ),

        BindingGroup(
          Set(Variable("B")),
          Some(Constant("3"))
        )
      )

    val resultMatchOption = SymbolicMatch.unify(left, right)

    assertMatching(resultMatchOption, left, right, expectedBindingGroups)
  }


  "f(3, A, 4, D) and f(B, C, C, A)" should "match" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Constant("3"),
          Variable("A"),
          Constant("4"),
          Variable("D")
        )
      )


    val right =
      CompoundFunctor(
        "f",
        List(
          Variable("B"),
          Variable("C"),
          Variable("C"),
          Variable("A")
        )
      )

    val expectedBindingGroups =
      Set(
        BindingGroup(
          Set(Variable("A"), Variable("C"), Variable("D")),
          Some(Constant("4"))
        ),

        BindingGroup(
          Set(Variable("B")),
          Some(Constant("3"))
        )
      )

    val resultMatchOption = SymbolicMatch.unify(left, right)

    assertMatching(resultMatchOption, left, right, expectedBindingGroups)
  }



  "f(3, A, 4, D, 5) and f(B, C, C, A, D)" should "NOT match" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Constant("3"),
          Variable("A"),
          Constant("4"),
          Variable("D"),
          Constant("5")
        )
      )


    val right =
      CompoundFunctor(
        "f",
        List(
          Variable("B"),
          Variable("C"),
          Variable("C"),
          Variable("A"),
          Variable("D")
        )
      )


    SymbolicMatch.unify(left, right) should be(None)
  }



  "f(3, A, 4, D, 4) and f(B, C, C, A, D)" should "match" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Constant("3"),
          Variable("A"),
          Constant("4"),
          Variable("D"),
          Constant("4")
        )
      )


    val right =
      CompoundFunctor(
        "f",
        List(
          Variable("B"),
          Variable("C"),
          Variable("C"),
          Variable("A"),
          Variable("D")
        )
      )

    val expectedBindingGroups =
      Set(
        BindingGroup(
          Set(Variable("A"), Variable("C"), Variable("D")),
          Some(Constant("4"))
        ),

        BindingGroup(
          Set(Variable("B")),
          Some(Constant("3"))
        )
      )

    val resultMatchOption = SymbolicMatch.unify(left, right)

    assertMatching(resultMatchOption, left, right, expectedBindingGroups)
  }



  "f(3, A, 4, D, 4, E) and f(B, C, C, A, D, F)" should "match" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Constant("3"),
          Variable("A"),
          Constant("4"),
          Variable("D"),
          Constant("4"),
          Variable("E")
        )
      )


    val right =
      CompoundFunctor(
        "f",
        List(
          Variable("B"),
          Variable("C"),
          Variable("C"),
          Variable("A"),
          Variable("D"),
          Variable("F")
        )
      )

    val expectedBindingGroups =
      Set(
        BindingGroup(
          Set(Variable("A"), Variable("C"), Variable("D")),
          Some(Constant("4"))
        ),

        BindingGroup(
          Set(Variable("B")),
          Some(Constant("3"))
        ),

        BindingGroup(
          Set(Variable("E"), Variable("F")),
          None
        )
      )

    val resultMatchOption = SymbolicMatch.unify(left, right)

    assertMatching(resultMatchOption, left, right, expectedBindingGroups)
  }


  "f(3, A, 4, D, 4, E, G) and f(B, C, C, A, D, F, B)" should "match" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Constant("3"),
          Variable("A"),
          Constant("4"),
          Variable("D"),
          Constant("4"),
          Variable("E"),
          Variable("G")
        )
      )


    val right =
      CompoundFunctor(
        "f",
        List(
          Variable("B"),
          Variable("C"),
          Variable("C"),
          Variable("A"),
          Variable("D"),
          Variable("F"),
          Variable("B")
        )
      )

    val expectedBindingGroups =
      Set(
        BindingGroup(
          Set(Variable("A"), Variable("C"), Variable("D")),
          Some(Constant("4"))
        ),

        BindingGroup(
          Set(Variable("B"), Variable("G")),
          Some(Constant("3"))
        ),

        BindingGroup(
          Set(Variable("E"), Variable("F")),
          None
        )
      )

    val resultMatchOption = SymbolicMatch.unify(left, right)

    assertMatching(resultMatchOption, left, right, expectedBindingGroups)
  }



  "f(3, A, 4, B) and f(B, A, 3, 3)" should "NOT match" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Constant("3"),
          Variable("A"),
          Constant("4"),
          Variable("B")
        )
      )


    val right =
      CompoundFunctor(
        "f",
        List(
          Variable("B"),
          Variable("A"),
          Constant("3"),
          Constant("3")
        )
      )

    SymbolicMatch.unify(left, right) should be(None)
  }


  "f(A) and A" should "NOT match" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Variable("A")
        )
      )


    val right =
      Variable("A")


    SymbolicMatch.unify(left, right) should be(None)
  }


  "f(a) and a" should "NOT match" in {
    val left =
      CompoundFunctor(
        "f",
        List(
          Constant("a")
        )
      )


    val right =
      Constant("a")


    SymbolicMatch.unify(left, right) should be(None)
  }


  private def assertMatching(
                              resultMatchOption: Option[SymbolicMatch],
                              left: Literal,
                              right: Literal,
                              expectedBindingGroups: Set[BindingGroup]): Unit = {
    resultMatchOption should not be empty

    resultMatchOption.foreach(resultMatch => {
      resultMatch.left should be(left)
      resultMatch.right should be(right)
      resultMatch.environment.bindingGroups should be(expectedBindingGroups)
    })
  }
}
