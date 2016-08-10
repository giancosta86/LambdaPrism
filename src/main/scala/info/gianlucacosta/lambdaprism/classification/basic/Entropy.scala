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

package info.gianlucacosta.lambdaprism.classification.basic

import info.gianlucacosta.helios.mathutils.Maths

object Entropy {
  /**
    * Computes the entropy of the given item set,
    * assuming the output attribute is the rightmost one.
    *
    * @param valueTable
    * @return
    * <ol>
    * <li>0, if all the items have the same output value</li>
    * <li>1, if all the output values are distributed on the same # of instances</li>
    * <li>the result of the floating-point formula, otherwise</li>
    * </ol>
    */
  def compute(valueTable: Table): Double = {
    val totalRowCount =
      valueTable.length

    val rowsGroupedByOutputAttribute =
      valueTable
        .groupBy(row => row.last)


    if (rowsGroupedByOutputAttribute.size == 1)
      0
    else {
      val allGroupsHaveTheSameSize =
        rowsGroupedByOutputAttribute
          .map(_._2.size)
          .toSet
          .size == 1

      if (allGroupsHaveTheSameSize)
        1
      else
        rowsGroupedByOutputAttribute
          .foldLeft(0.0)((cumulatedEntropy, outputGroup) => {
            val groupRows =
              outputGroup._2

            val groupWeight =
              groupRows.length.toDouble / totalRowCount

            cumulatedEntropy - groupWeight * Maths.log(2)(groupWeight)
          })
    }
  }
}
