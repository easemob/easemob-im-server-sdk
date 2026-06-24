package com.easemob.im.api;

import com.easemob.im.ApiCallback;
import com.easemob.im.ApiClient;
import com.easemob.im.ApiException;
import com.easemob.im.ApiResponse;
import com.easemob.im.Configuration;
import com.easemob.im.Pair;

import com.easemob.im.api.model.*;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionApi {
    private ApiClient localVarApiClient;
    private int localHostIndex;
    private String localCustomBaseUrl;

    public CollectionApi() {
        this(Configuration.getDefaultApiClient());
    }

    public CollectionApi(ApiClient apiClient) {
        this.localVarApiClient = apiClient;
    }

    public ApiClient getApiClient() { return localVarApiClient; }
    public void setApiClient(ApiClient apiClient) { this.localVarApiClient = apiClient; }
    public int getHostIndex() { return localHostIndex; }
    public void setHostIndex(int hostIndex) { this.localHostIndex = hostIndex; }
    public String getCustomBaseUrl() { return localCustomBaseUrl; }
    public void setCustomBaseUrl(String customBaseUrl) { this.localCustomBaseUrl = customBaseUrl; }

    /**
     * Build call for userCollectionAddSingle
     * @param username  (required)
     * @param body  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call userCollectionAddSingleCall(String username, EMUserCollectionAddSingle body, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        String[] localBasePaths = new String[] {};
        if (localCustomBaseUrl != null) {
            basePath = localCustomBaseUrl;
        } else if (localBasePaths.length > 0) {
            basePath = localBasePaths[localHostIndex];
        }

        Object localVarPostBody = body;
        String localVarPath = "/users/{username}/collections"
            .replace("{" + "username" + "}", localVarApiClient.escapeString(username.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = { "application/json" };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = { "application/json" };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {};
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call userCollectionAddSingleValidateBeforeCall(String username, EMUserCollectionAddSingle body, final ApiCallback _callback) throws ApiException {
        if (username == null) throw new ApiException("Missing the required parameter 'username' when calling userCollectionAddSingle(Async)");
        if (body == null) throw new ApiException("Missing the required parameter 'body' when calling userCollectionAddSingle(Async)");
        return userCollectionAddSingleCall(username, body, _callback);
    }

    /**
     * 添加一条收藏
     * @param username  (required)
     * @param body  (required)
     * @return EMUserCollectionAddSingleResult
     * @throws ApiException If fail to call the API
     */
    public EMUserCollectionAddSingleResult userCollectionAddSingle(String username, EMUserCollectionAddSingle body) throws ApiException {
        ApiResponse<EMUserCollectionAddSingleResult> localVarResp = userCollectionAddSingleWithHttpInfo(username, body);
        return localVarResp.getData();
    }

    public ApiResponse<EMUserCollectionAddSingleResult> userCollectionAddSingleWithHttpInfo(String username, EMUserCollectionAddSingle body) throws ApiException {
        okhttp3.Call localVarCall = userCollectionAddSingleValidateBeforeCall(username, body, null);
        Type localVarReturnType = new TypeToken<EMUserCollectionAddSingleResult>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    public okhttp3.Call userCollectionAddSingleAsync(String username, EMUserCollectionAddSingle body, final ApiCallback<EMUserCollectionAddSingleResult> _callback) throws ApiException {
        okhttp3.Call localVarCall = userCollectionAddSingleValidateBeforeCall(username, body, _callback);
        Type localVarReturnType = new TypeToken<EMUserCollectionAddSingleResult>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    /**
     * Build call for userCollectionAddBatch
     * @param body  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call userCollectionAddBatchCall(EMUserCollectionAddBatch body, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        String[] localBasePaths = new String[] {};
        if (localCustomBaseUrl != null) {
            basePath = localCustomBaseUrl;
        } else if (localBasePaths.length > 0) {
            basePath = localBasePaths[localHostIndex];
        }

        Object localVarPostBody = body;
        String localVarPath = "/collections";

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = { "application/json" };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = { "application/json" };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {};
        return localVarApiClient.buildCall(basePath, localVarPath, "POST", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call userCollectionAddBatchValidateBeforeCall(EMUserCollectionAddBatch body, final ApiCallback _callback) throws ApiException {
        if (body == null) throw new ApiException("Missing the required parameter 'body' when calling userCollectionAddBatch(Async)");
        return userCollectionAddBatchCall(body, _callback);
    }

    /**
     * 批量添加用户收藏
     * @param body  (required)
     * @return EMUserCollectionAddBatchResult
     * @throws ApiException If fail to call the API
     */
    public EMUserCollectionAddBatchResult userCollectionAddBatch(EMUserCollectionAddBatch body) throws ApiException {
        ApiResponse<EMUserCollectionAddBatchResult> localVarResp = userCollectionAddBatchWithHttpInfo(body);
        return localVarResp.getData();
    }

    public ApiResponse<EMUserCollectionAddBatchResult> userCollectionAddBatchWithHttpInfo(EMUserCollectionAddBatch body) throws ApiException {
        okhttp3.Call localVarCall = userCollectionAddBatchValidateBeforeCall(body, null);
        Type localVarReturnType = new TypeToken<EMUserCollectionAddBatchResult>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    public okhttp3.Call userCollectionAddBatchAsync(EMUserCollectionAddBatch body, final ApiCallback<EMUserCollectionAddBatchResult> _callback) throws ApiException {
        okhttp3.Call localVarCall = userCollectionAddBatchValidateBeforeCall(body, _callback);
        Type localVarReturnType = new TypeToken<EMUserCollectionAddBatchResult>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    /**
     * Build call for userCollectionExtModify
     * @param username  (required)
     * @param collectionId  (required)
     * @param body  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call userCollectionExtModifyCall(String username, String collectionId, EMUserCollectionExtModify body, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        String[] localBasePaths = new String[] {};
        if (localCustomBaseUrl != null) {
            basePath = localCustomBaseUrl;
        } else if (localBasePaths.length > 0) {
            basePath = localBasePaths[localHostIndex];
        }

        Object localVarPostBody = body;
        String localVarPath = "/users/{username}/collections/{collectionId}"
            .replace("{" + "username" + "}", localVarApiClient.escapeString(username.toString()))
            .replace("{" + "collectionId" + "}", localVarApiClient.escapeString(collectionId.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = { "application/json" };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = { "application/json" };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {};
        return localVarApiClient.buildCall(basePath, localVarPath, "PUT", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call userCollectionExtModifyValidateBeforeCall(String username, String collectionId, EMUserCollectionExtModify body, final ApiCallback _callback) throws ApiException {
        if (username == null) throw new ApiException("Missing the required parameter 'username' when calling userCollectionExtModify(Async)");
        if (collectionId == null) throw new ApiException("Missing the required parameter 'collectionId' when calling userCollectionExtModify(Async)");
        if (body == null) throw new ApiException("Missing the required parameter 'body' when calling userCollectionExtModify(Async)");
        return userCollectionExtModifyCall(username, collectionId, body, _callback);
    }

    /**
     * 修改收藏扩展信息
     * @param username  (required)
     * @param collectionId  (required)
     * @param body  (required)
     * @return EMUserCollectionExtModifyResult
     * @throws ApiException If fail to call the API
     */
    public EMUserCollectionExtModifyResult userCollectionExtModify(String username, String collectionId, EMUserCollectionExtModify body) throws ApiException {
        ApiResponse<EMUserCollectionExtModifyResult> localVarResp = userCollectionExtModifyWithHttpInfo(username, collectionId, body);
        return localVarResp.getData();
    }

    public ApiResponse<EMUserCollectionExtModifyResult> userCollectionExtModifyWithHttpInfo(String username, String collectionId, EMUserCollectionExtModify body) throws ApiException {
        okhttp3.Call localVarCall = userCollectionExtModifyValidateBeforeCall(username, collectionId, body, null);
        Type localVarReturnType = new TypeToken<EMUserCollectionExtModifyResult>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    public okhttp3.Call userCollectionExtModifyAsync(String username, String collectionId, EMUserCollectionExtModify body, final ApiCallback<EMUserCollectionExtModifyResult> _callback) throws ApiException {
        okhttp3.Call localVarCall = userCollectionExtModifyValidateBeforeCall(username, collectionId, body, _callback);
        Type localVarReturnType = new TypeToken<EMUserCollectionExtModifyResult>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    /**
     * Build call for userCollectionDelete
     * @param username  (required)
     * @param body  (required)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call userCollectionDeleteCall(String username, EMUserCollectionDelete body, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        String[] localBasePaths = new String[] {};
        if (localCustomBaseUrl != null) {
            basePath = localCustomBaseUrl;
        } else if (localBasePaths.length > 0) {
            basePath = localBasePaths[localHostIndex];
        }

        Object localVarPostBody = body;
        String localVarPath = "/users/{username}/collections"
            .replace("{" + "username" + "}", localVarApiClient.escapeString(username.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = { "application/json" };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = { "application/json" };
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {};
        return localVarApiClient.buildCall(basePath, localVarPath, "DELETE", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call userCollectionDeleteValidateBeforeCall(String username, EMUserCollectionDelete body, final ApiCallback _callback) throws ApiException {
        if (username == null) throw new ApiException("Missing the required parameter 'username' when calling userCollectionDelete(Async)");
        if (body == null) throw new ApiException("Missing the required parameter 'body' when calling userCollectionDelete(Async)");
        return userCollectionDeleteCall(username, body, _callback);
    }

    /**
     * 删除用户收藏
     * @param username  (required)
     * @param body  (required)
     * @return EMUserCollectionDeleteResult
     * @throws ApiException If fail to call the API
     */
    public EMUserCollectionDeleteResult userCollectionDelete(String username, EMUserCollectionDelete body) throws ApiException {
        ApiResponse<EMUserCollectionDeleteResult> localVarResp = userCollectionDeleteWithHttpInfo(username, body);
        return localVarResp.getData();
    }

    public ApiResponse<EMUserCollectionDeleteResult> userCollectionDeleteWithHttpInfo(String username, EMUserCollectionDelete body) throws ApiException {
        okhttp3.Call localVarCall = userCollectionDeleteValidateBeforeCall(username, body, null);
        Type localVarReturnType = new TypeToken<EMUserCollectionDeleteResult>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    public okhttp3.Call userCollectionDeleteAsync(String username, EMUserCollectionDelete body, final ApiCallback<EMUserCollectionDeleteResult> _callback) throws ApiException {
        okhttp3.Call localVarCall = userCollectionDeleteValidateBeforeCall(username, body, _callback);
        Type localVarReturnType = new TypeToken<EMUserCollectionDeleteResult>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }

    /**
     * Build call for userCollectionGet
     * @param username  (required)
     * @param beginTime  (optional)
     * @param endTime  (optional)
     * @param direction  (optional)
     * @param type  (optional)
     * @param limit  (optional)
     * @param collectionId  (optional)
     * @param _callback Callback for upload/download progress
     * @return Call to execute
     * @throws ApiException If fail to serialize the request body object
     */
    public okhttp3.Call userCollectionGetCall(String username, Long beginTime, Long endTime, String direction, Integer type, Integer limit, String collectionId, final ApiCallback _callback) throws ApiException {
        String basePath = null;
        String[] localBasePaths = new String[] {};
        if (localCustomBaseUrl != null) {
            basePath = localCustomBaseUrl;
        } else if (localBasePaths.length > 0) {
            basePath = localBasePaths[localHostIndex];
        }

        Object localVarPostBody = null;
        String localVarPath = "/users/{username}/collections"
            .replace("{" + "username" + "}", localVarApiClient.escapeString(username.toString()));

        List<Pair> localVarQueryParams = new ArrayList<Pair>();
        List<Pair> localVarCollectionQueryParams = new ArrayList<Pair>();
        if (beginTime != null) localVarQueryParams.addAll(localVarApiClient.parameterToPair("begin_time", beginTime));
        if (endTime != null) localVarQueryParams.addAll(localVarApiClient.parameterToPair("end_time", endTime));
        if (direction != null) localVarQueryParams.addAll(localVarApiClient.parameterToPair("direction", direction));
        if (type != null) localVarQueryParams.addAll(localVarApiClient.parameterToPair("type", type));
        if (limit != null) localVarQueryParams.addAll(localVarApiClient.parameterToPair("limit", limit));
        if (collectionId != null) localVarQueryParams.addAll(localVarApiClient.parameterToPair("collection_id", collectionId));

        Map<String, String> localVarHeaderParams = new HashMap<String, String>();
        Map<String, String> localVarCookieParams = new HashMap<String, String>();
        Map<String, Object> localVarFormParams = new HashMap<String, Object>();

        final String[] localVarAccepts = { "application/json" };
        final String localVarAccept = localVarApiClient.selectHeaderAccept(localVarAccepts);
        if (localVarAccept != null) localVarHeaderParams.put("Accept", localVarAccept);

        final String[] localVarContentTypes = {};
        final String localVarContentType = localVarApiClient.selectHeaderContentType(localVarContentTypes);
        if (localVarContentType != null) localVarHeaderParams.put("Content-Type", localVarContentType);

        String[] localVarAuthNames = new String[] {};
        return localVarApiClient.buildCall(basePath, localVarPath, "GET", localVarQueryParams, localVarCollectionQueryParams, localVarPostBody, localVarHeaderParams, localVarCookieParams, localVarFormParams, localVarAuthNames, _callback);
    }

    @SuppressWarnings("rawtypes")
    private okhttp3.Call userCollectionGetValidateBeforeCall(String username, Long beginTime, Long endTime, String direction, Integer type, Integer limit, String collectionId, final ApiCallback _callback) throws ApiException {
        if (username == null) throw new ApiException("Missing the required parameter 'username' when calling userCollectionGet(Async)");
        return userCollectionGetCall(username, beginTime, endTime, direction, type, limit, collectionId, _callback);
    }

    /**
     * 分页获取用户收藏
     * @param username  (required)
     * @param beginTime  (optional)
     * @param endTime  (optional)
     * @param direction  (optional)
     * @param type  (optional)
     * @param limit  (optional)
     * @param collectionId  (optional)
     * @return EMUserCollectionGetResult
     * @throws ApiException If fail to call the API
     */
    public EMUserCollectionGetResult userCollectionGet(String username, Long beginTime, Long endTime, String direction, Integer type, Integer limit, String collectionId) throws ApiException {
        ApiResponse<EMUserCollectionGetResult> localVarResp = userCollectionGetWithHttpInfo(username, beginTime, endTime, direction, type, limit, collectionId);
        return localVarResp.getData();
    }

    public ApiResponse<EMUserCollectionGetResult> userCollectionGetWithHttpInfo(String username, Long beginTime, Long endTime, String direction, Integer type, Integer limit, String collectionId) throws ApiException {
        okhttp3.Call localVarCall = userCollectionGetValidateBeforeCall(username, beginTime, endTime, direction, type, limit, collectionId, null);
        Type localVarReturnType = new TypeToken<EMUserCollectionGetResult>(){}.getType();
        return localVarApiClient.execute(localVarCall, localVarReturnType);
    }

    public okhttp3.Call userCollectionGetAsync(String username, Long beginTime, Long endTime, String direction, Integer type, Integer limit, String collectionId, final ApiCallback<EMUserCollectionGetResult> _callback) throws ApiException {
        okhttp3.Call localVarCall = userCollectionGetValidateBeforeCall(username, beginTime, endTime, direction, type, limit, collectionId, _callback);
        Type localVarReturnType = new TypeToken<EMUserCollectionGetResult>(){}.getType();
        localVarApiClient.executeAsync(localVarCall, localVarReturnType, _callback);
        return localVarCall;
    }
}
