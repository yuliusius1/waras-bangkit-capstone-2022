# {{classname}}

All URIs are relative to **

Method | HTTP request | Description
------------- | ------------- | -------------
[**WarasGet**](WarasApi.md#WarasGet) | **Get** /Waras | Get all Waras
[**WarasPost**](WarasApi.md#WarasPost) | **Post** /Waras | Create new Waras
[**WarasWarasIdDelete**](WarasApi.md#WarasWarasIdDelete) | **Delete** /Waras/{WarasId} | Delete existing Waras
[**WarasWarasIdPut**](WarasApi.md#WarasWarasIdPut) | **Put** /Waras/{WarasId} | Update existing Waras

# **WarasGet**
> []Waras WarasGet(ctx, optional)
Get all Waras

Get all active Waras by default

### Required Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ctx** | **context.Context** | context for authentication, logging, cancellation, deadlines, tracing, etc.
 **optional** | ***WarasApiWarasGetOpts** | optional parameters | nil if no parameters

### Optional Parameters
Optional parameters are passed through a pointer to a WarasApiWarasGetOpts struct
Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **includeDone** | **optional.Bool**| Is include done Waras | [default to false]
 **name** | **optional.String**| Filter Waras by name | 

### Return type

[**[]Waras**](array.md)

### Authorization

[WarasAuth](../README.md#WarasAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **WarasPost**
> Waras WarasPost(ctx, body)
Create new Waras

Create new Waras to database

### Required Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ctx** | **context.Context** | context for authentication, logging, cancellation, deadlines, tracing, etc.
  **body** | [**CreateOrUpdateWaras**](CreateOrUpdateWaras.md)|  | 

### Return type

[**Waras**](Waras.md)

### Authorization

[WarasAuth](../README.md#WarasAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **WarasWarasIdDelete**
> InlineResponse200 WarasWarasIdDelete(ctx, warasId)
Delete existing Waras

Delete existing Waras in database

### Required Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ctx** | **context.Context** | context for authentication, logging, cancellation, deadlines, tracing, etc.
  **warasId** | **string**| Waras id for updated | 

### Return type

[**InlineResponse200**](inline_response_200.md)

### Authorization

[WarasAuth](../README.md#WarasAuth)

### HTTP request headers

 - **Content-Type**: Not defined
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

# **WarasWarasIdPut**
> Waras WarasWarasIdPut(ctx, body, warasId)
Update existing Waras

Update existing Waras in database

### Required Parameters

Name | Type | Description  | Notes
------------- | ------------- | ------------- | -------------
 **ctx** | **context.Context** | context for authentication, logging, cancellation, deadlines, tracing, etc.
  **body** | [**CreateOrUpdateWaras**](CreateOrUpdateWaras.md)|  | 
  **warasId** | **string**| Waras id for updated | 

### Return type

[**Waras**](Waras.md)

### Authorization

[WarasAuth](../README.md#WarasAuth)

### HTTP request headers

 - **Content-Type**: application/json
 - **Accept**: application/json

[[Back to top]](#) [[Back to API list]](../README.md#documentation-for-api-endpoints) [[Back to Model list]](../README.md#documentation-for-models) [[Back to README]](../README.md)

