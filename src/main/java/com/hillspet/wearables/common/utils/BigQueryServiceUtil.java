package com.hillspet.wearables.common.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Base64;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.bigquery.BigQuery;
import com.google.cloud.bigquery.BigQueryOptions;
import com.google.cloud.bigquery.FieldValueList;
import com.google.cloud.bigquery.Job;
import com.google.cloud.bigquery.JobException;
import com.google.cloud.bigquery.JobId;
import com.google.cloud.bigquery.JobInfo;
import com.google.cloud.bigquery.QueryJobConfiguration;
import com.google.cloud.bigquery.TableResult;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

public class BigQueryServiceUtil {
	private static final Logger LOGGER = LogManager.getLogger(BigQueryServiceUtil.class);

	static BigQuery bigQuery = null;
	private static final String ANALYTICAL_SA_JSON_STRING = System.getenv("ANALYTICAL_SA_JSON_STRING");
	public static BigQuery getInstance() {
		try {
			if (bigQuery == null) {
				String ANALYTICAL_SA_JSON_PLAIN_TEXT = new String(
						Base64.getDecoder().decode(ANALYTICAL_SA_JSON_STRING));
				InputStream serviceAccount = new ByteArrayInputStream(ANALYTICAL_SA_JSON_PLAIN_TEXT.getBytes());
				bigQuery = BigQueryOptions.newBuilder()
						.setCredentials(ServiceAccountCredentials.fromStream(serviceAccount)).build().getService();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bigQuery;
	}

	public void updateBigQueryTable(String query) {
		try {
			LOGGER.info("SYSOUT :: Start - Big Query Table Updated. Query Executed");
			QueryJobConfiguration updateBigQueryTable = QueryJobConfiguration.newBuilder(query).setUseLegacySql(false)
					.build();
			getInstance().query(updateBigQueryTable);
			// System.out.println("Big Query Table Updated. Query Executed:"+query);
			System.out.println("Big Query Table Updated. Query Executed");

		} catch (JobException e) {
			System.out.println("Job Exception in updateBigQueryTable method:" + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("Interrupted Exception in updateBigQueryTable method:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception in updateBigQueryTable method:" + e.getMessage());
		}
	}

	public static TableResult queryBigQueryTable(String query) {
		TableResult result = null;

		try {
			QueryJobConfiguration queryConfig = QueryJobConfiguration.newBuilder(query).setUseLegacySql(false).build();
			JobId jobId = JobId.of(UUID.randomUUID().toString());
			Job queryJob = getInstance().create(JobInfo.newBuilder(queryConfig).setJobId(jobId).build());
			// Wait for the query to complete.
			queryJob = queryJob.waitFor();
			// Check for errors
			if (queryJob == null) {
				System.out.println("Query Job no longer exists");
			} else if (queryJob.getStatus().getError() != null) {
				// You can also look at queryJob.getStatus().getExecutionErrors() for all
				// errors, not just the latest one.
				System.out.println("Error:" + queryJob.getStatus().getError().toString());
			}
			result = queryJob.getQueryResults();
		} catch (JobException e) {
			System.out.println("Interrupted Exception in queryBigQueryTable method:" + e.getMessage());
		} catch (InterruptedException e) {
			System.out.println("Interrupted Exception in queryBigQueryTable method:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception in queryBigQueryTable method:" + e.getMessage());
		}

		return result;
	}

	public String queryBigQueryTable(JSONObject parameters) {
		JSONArray dataArray = new JSONArray();
		String query = "";

		JSONArray errorArray = new JSONArray();
		JSONObject result = new JSONObject();

		try {
			boolean parametersValid = validateParameters(parameters);
			// System.out.println("parametersValid:" + parametersValid);
			if (parametersValid) {
				boolean doesTableExist = checkIfTableExists(parameters.getString("fromTables"));
				// System.out.println("doesTableExist:" + doesTableExist);
				if (doesTableExist) {
					String conditionsString = "";
					JSONArray conditions = parameters.getJSONArray("conditions");
					long invalidTableCondition = 0L;
					for (Object object : conditions) {
						JSONObject tableConditionObj = (JSONObject) object;
						boolean isTableConditionValid = validateTableCondition(tableConditionObj);
						// System.out.println("Table Condition Valid:" + isTableConditionValid + "
						// Condition:" + tableConditionObj);

						if (isTableConditionValid) {
							if (conditions.length() == 1) {
								if (tableConditionObj.getString("type") != null
										&& tableConditionObj.getString("type").equals("string")) {
									conditionsString += tableConditionObj.getString("key") + " "
											+ tableConditionObj.getString("operator") + " '"
											+ tableConditionObj.getString("value") + "'";
								} else {
									conditionsString += tableConditionObj.getString("key") + " "
											+ tableConditionObj.getString("operator") + " "
											+ tableConditionObj.getString("value");
								}
							} else {
								if (tableConditionObj.getString("type") != null
										&& tableConditionObj.getString("type").equals("string")) {
									conditionsString += " AND " + tableConditionObj.getString("key") + " "
											+ tableConditionObj.getString("operator") + " '"
											+ tableConditionObj.getString("value") + "'";
								} else {
									conditionsString += " AND " + tableConditionObj.getString("key") + " "
											+ tableConditionObj.getString("operator") + " "
											+ tableConditionObj.getString("value");
								}
							}
						} else {
							++invalidTableCondition;
						}
					}

					if (invalidTableCondition > 0) {
						errorArray.put("INVALID_TABLE_CONDITION");
					}

					String selectClauseString = parameters.getString("selectClause");
					query = "SELECT " + selectClauseString + " FROM " + parameters.getString("fromTables") + " WHERE "
							+ conditionsString;
					// System.out.println("Query:" + query);
					TableResult tableResult = queryBigQueryTable(query);

					if (tableResult != null && tableResult.getTotalRows() > 0) {
						dataArray = getTableResultJSON(tableResult, selectClauseString);
					} else {
						errorArray.put("INVALID_QUERY/NO_DATA_FOUND");
					}
				} else {
					errorArray.put("INVALID_TABLE_NAME");
				}
			} else {
				errorArray.put("INVALID_PARAMETER");
			}

			if (errorArray.length() > 0) {
				result.put("result", "error");
				result.put("errors", errorArray);
			} else {
				result.put("result", "success");
			}

			result.put("data", dataArray);

		} catch (JobException e) {
			System.out.println("Interrupted Exception in queryBigQueryTable method:" + e.getMessage());
		} catch (Exception e) {
			System.out.println("Exception in queryBigQueryTable method:" + e.getMessage());
		}

		return result.toString();
	}

	private boolean validateParameters(JSONObject parameters) {
		boolean isValid = false;

		try {
			// We are checking if select clause and from clause are included
			if (parameters == null || parameters.length() < 3) {
				isValid = false;
			} else {
				int count = 0;
				if (parameters.getString("fromTables") != null && !parameters.getString("fromTables").equals("")) {
					++count;
				}
				if (parameters.getString("selectClause") != null && !parameters.getString("selectClause").equals("")
						&& !parameters.getString("selectClause").equals("*")) {
					++count;
				}
				if (parameters.getJSONArray("conditions") != null
						&& parameters.getJSONArray("conditions").length() >= 1) {
					++count;
				}
				if (count == 3) {
					isValid = true;
				} else {
					isValid = false;
				}
			}
		} catch (JSONException e) {
			System.out.println("JSONException in validateParameters method:" + e.getMessage());
		}

		return isValid;
	}

	private boolean checkIfTableExists(String tablesNames) {
		boolean doesExist = false;

		String query = "SELECT * FROM " + tablesNames + " LIMIT 1";

		TableResult tablesData = queryBigQueryTable(query);

		if (tablesData != null && tablesData.getTotalRows() > 0) {
			doesExist = true;
		}

		return doesExist;
	}

	public static boolean validateTableCondition(JSONObject parameters) {
		boolean isValid = false;

		try {
			// We are checking if select clause and from clause are included
			if (parameters == null || parameters.length() < 3) {
				isValid = false;
			} else {
				int count = 0;
				if (parameters.getString("key") != null && !parameters.getString("key").equals("")) {
					++count;
				}
				if (parameters.getString("value") != null && !parameters.getString("value").equals("")) {
					++count;
				}
				if (parameters.getString("operator") != null && !parameters.getString("operator").equals("")) {
					++count;
				}
				if (count >= 3) {
					isValid = true;
				} else {
					isValid = false;
				}
			}
		} catch (JSONException e) {
			System.out.println("JSONException in validateTableCondition method:" + e.getMessage());
		}

		return isValid;
	}

	public static String getTableResultString(TableResult tableResult) {

		JSONArray result = new JSONArray();

		if (tableResult != null && tableResult.getTotalRows() > 0) {
			for (FieldValueList parentRow : tableResult.iterateAll()) {
				result.put(parentRow);
			}
		}
		return result.toString();
	}

	public static JSONArray getTableResultJSON(TableResult tableResult, String selectClauseString) {

		JSONArray result = new JSONArray();

		if (tableResult != null && tableResult.getTotalRows() > 0) {
			for (FieldValueList parentRow : tableResult.iterateAll()) {
				JSONObject element = new JSONObject();
				if (selectClauseString.indexOf(",") > -1) {
					String[] clauseArray = selectClauseString.split(",");
					for (String clause : clauseArray) {
						element.put(clause, parentRow.get(clause).getValue());
					}
				}
				result.put(element);
			}
		}
		return result;
	}
}