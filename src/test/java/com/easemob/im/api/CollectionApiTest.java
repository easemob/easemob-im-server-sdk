package com.easemob.im.api;

import com.easemob.im.ApiException;
import com.easemob.im.api.model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * API tests for CollectionApi
 */
public class CollectionApiTest extends AbstractTest {

    private final CollectionApi api = new CollectionApi();
    private final UserApi userApi = new UserApi();

    public CollectionApiTest() {
    }

    /**
     * 添加一条收藏
     *
     * https://doc.easemob.com/document/server-side/user_collection_add_single.html
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void userCollectionAddSingleTest() throws ApiException {
        String username = randomUserName();
        EMCreateUser createUser = new EMCreateUser();
        createUser.setUsername(username);
        createUser.setPassword("123456");
        assertDoesNotThrow(() -> userApi.createUsers(Arrays.asList(createUser)));

        EMUserCollectionAddSingle body = new EMUserCollectionAddSingle();
        body.setData("test data");
        body.setType(0);

        EMUserCollectionAddSingleResult response = api.userCollectionAddSingle(username, body);
        assertNotNull(response);
        assertNotNull(response.getCollection());
        assertNotNull(response.getCollection().getId());

        String collectionId = response.getCollection().getId();

        EMUserCollectionDelete deleteBody = new EMUserCollectionDelete();
        deleteBody.setCollectionIds(Arrays.asList(collectionId));
        assertDoesNotThrow(() -> api.userCollectionDelete(username, deleteBody));

        assertDoesNotThrow(() -> userApi.deleteUser(username));
    }

    /**
     * 批量添加用户收藏
     *
     * https://doc.easemob.com/document/server-side/user_collection_add_batch.html
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void userCollectionAddBatchTest() throws ApiException {
        String username = randomUserName();
        EMCreateUser createUser = new EMCreateUser();
        createUser.setUsername(username);
        createUser.setPassword("123456");
        assertDoesNotThrow(() -> userApi.createUsers(Arrays.asList(createUser)));

        EMCollectionItem item1 = new EMCollectionItem();
        item1.setId("batch-id-1");
        item1.setData("batch data 1");
        item1.setType(0);
        item1.setExt("");
        item1.setCreatedAt(System.currentTimeMillis());

        EMCollectionItem item2 = new EMCollectionItem();
        item2.setId("batch-id-2");
        item2.setData("batch data 2");
        item2.setType(1);
        item2.setExt("");
        item2.setCreatedAt(System.currentTimeMillis());

        EMUserCollectionAddBatch body = new EMUserCollectionAddBatch();
        body.setUsername(username);
        body.setCollections(Arrays.asList(item1, item2));

        EMUserCollectionAddBatchResult response = api.userCollectionAddBatch(body);
        assertNotNull(response);
        assertNotNull(response.getCollections());
        assertFalse(response.getCollections().isEmpty());

        List<String> ids = new ArrayList<>();
        for (EMCollectionResource r : response.getCollections()) {
            ids.add(r.getId());
        }
        EMUserCollectionDelete deleteBody = new EMUserCollectionDelete();
        deleteBody.setCollectionIds(ids);
        assertDoesNotThrow(() -> api.userCollectionDelete(username, deleteBody));

        assertDoesNotThrow(() -> userApi.deleteUser(username));
    }

    /**
     * 修改收藏扩展信息
     *
     * https://doc.easemob.com/document/server-side/user_collection_ext_modify.html
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void userCollectionExtModifyTest() throws ApiException {
        String username = randomUserName();
        EMCreateUser createUser = new EMCreateUser();
        createUser.setUsername(username);
        createUser.setPassword("123456");
        assertDoesNotThrow(() -> userApi.createUsers(Arrays.asList(createUser)));

        EMUserCollectionAddSingle addBody = new EMUserCollectionAddSingle();
        addBody.setData("test data");
        addBody.setType(0);
        EMUserCollectionAddSingleResult addResult = api.userCollectionAddSingle(username, addBody);
        String collectionId = addResult.getCollection().getId();

        EMUserCollectionExtModify body = new EMUserCollectionExtModify();
        body.setExt("modified ext");

        EMUserCollectionExtModifyResult response = api.userCollectionExtModify(username, collectionId, body);
        assertNotNull(response);
        assertNotNull(response.getCollection());
        assertEquals(collectionId, response.getCollection().getId());

        EMUserCollectionDelete deleteBody = new EMUserCollectionDelete();
        deleteBody.setCollectionIds(Arrays.asList(collectionId));
        assertDoesNotThrow(() -> api.userCollectionDelete(username, deleteBody));

        assertDoesNotThrow(() -> userApi.deleteUser(username));
    }

    /**
     * 删除用户收藏
     *
     * https://doc.easemob.com/document/server-side/user_collection_delete.html
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void userCollectionDeleteTest() throws ApiException {
        String username = randomUserName();
        EMCreateUser createUser = new EMCreateUser();
        createUser.setUsername(username);
        createUser.setPassword("123456");
        assertDoesNotThrow(() -> userApi.createUsers(Arrays.asList(createUser)));

        EMUserCollectionAddSingle addBody = new EMUserCollectionAddSingle();
        addBody.setData("test data");
        addBody.setType(0);
        EMUserCollectionAddSingleResult addResult = api.userCollectionAddSingle(username, addBody);
        String collectionId = addResult.getCollection().getId();

        EMUserCollectionDelete body = new EMUserCollectionDelete();
        body.setCollectionIds(Arrays.asList(collectionId));

        EMUserCollectionDeleteResult response = api.userCollectionDelete(username, body);
        assertNotNull(response);
        assertTrue(response.getResult());

        assertDoesNotThrow(() -> userApi.deleteUser(username));
    }

    /**
     * 分页获取用户收藏
     *
     * https://doc.easemob.com/document/server-side/user_collection_get.html
     *
     * @throws ApiException if the Api call fails
     */
    @Test
    public void userCollectionGetTest() throws ApiException {
        String username = randomUserName();
        EMCreateUser createUser = new EMCreateUser();
        createUser.setUsername(username);
        createUser.setPassword("123456");
        assertDoesNotThrow(() -> userApi.createUsers(Arrays.asList(createUser)));

        EMUserCollectionAddSingle addBody = new EMUserCollectionAddSingle();
        addBody.setData("test data");
        addBody.setType(0);
        EMUserCollectionAddSingleResult addResult = api.userCollectionAddSingle(username, addBody);
        String collectionId = addResult.getCollection().getId();

        EMUserCollectionGetResult response = api.userCollectionGet(username, null, null, null, null, null, null);
        assertNotNull(response);
        assertNotNull(response.getCollections());
        assertFalse(response.getCollections().isEmpty());

        EMUserCollectionDelete deleteBody = new EMUserCollectionDelete();
        deleteBody.setCollectionIds(Arrays.asList(collectionId));
        assertDoesNotThrow(() -> api.userCollectionDelete(username, deleteBody));

        assertDoesNotThrow(() -> userApi.deleteUser(username));
    }
}
