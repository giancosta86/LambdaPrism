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


object ClassificationProblem {
  /**
    * When an attribute is assigned this placeholder value,
    * it is assumed that the actual value is unknown
    */
  val UnknownValue = "?"
}

/**
  * Classification problem
  *
  * @param attributes A list of attributes - at least 2.
  *                   The rightmost attribute is <em>always</em> the <b>output</b> attribute
  * @param table      A list of rows describing items. Each row must have exactly N values,
  *                   where N is the # of the attributes declared for the problem.
  */
case class ClassificationProblem(
                                  attributes: List[Attribute],
                                  table: Table
                                ) {
  require(
    attributes.length >= 2,
    "There must be at least 2 attributes"
  )

  attributes.foreach(attribute =>
    require(
      attribute.nonEmpty,
      "Attribute names must be non-empty strings"
    )
  )

  require(
    attributes.distinct.length == attributes.length,
    "One or more attributes have duplicated names"
  )


  require(
    table.nonEmpty,
    "At least one data row must be provided"
  )

  table.foreach(row =>
    require(
      row.length == attributes.length,
      s"Row [${row.mkString(", ")}] has ${row.length} values, whereas the attributes are ${attributes.length}"
    )
  )

  /**
    * Attributes used to infer the output attribute for future instances;
    * all attributes except the last one are input attributes
    */
  val inputAttributes: List[Attribute] =
    attributes.take(attributes.length - 1)

  /**
    * Label used to classify instances; it's always the right-most attribute
    */
  val outputAttribute: Attribute =
    attributes.last


  /**
    * The set of all the values assigned to the output attribute in the item set
    */
  val outputValues: Set[Value] =
    table
      .map(_.last)
      .toSet


  private val outputCountMap: Map[Value, Int] =
    table
      .groupBy(row => row.last)
      .map {
        case (outputValue, outputItems) =>
          outputValue -> outputItems.length
      }


  def getOutputCount(outputValue: Value): Int =
    outputCountMap.getOrElse(outputValue, 0)


  /**
    * The global entropy of the item set
    */
  val entropy: Double =
    Entropy.compute(table)


  /**
    * Returns a ClassificationView based on the given input attribute
    *
    * @param attribute
    * @return
    */
  def getView(attribute: Attribute): ClassificationView = {
    val attributeIndex =
      inputAttributes.indexOf(attribute)

    require(
      attributeIndex > -1,
      s"Unknown input attribute: ${attribute}"
    )

    val subtableMap =
      table.groupBy(row =>
        row(attributeIndex)
      )


    DefaultClassificationView(
      this,
      attribute,
      subtableMap
    )
  }

  override val toString: String = {
    val attributesString =
      attributes.mkString(", ")


    val itemsString =
      table
        .map(row =>
          row.mkString(", ")
        )
        .mkString("\n")

    s"${attributesString}\n\n${itemsString}"
  }
}
