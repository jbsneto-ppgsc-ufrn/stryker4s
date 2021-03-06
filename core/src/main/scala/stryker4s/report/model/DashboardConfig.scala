package stryker4s.report.model

import stryker4s.config.DashboardReportType

case class DashboardConfig(
    apiKey: String,
    baseUrl: String,
    reportType: DashboardReportType,
    project: String,
    version: String,
    module: Option[String]
)
