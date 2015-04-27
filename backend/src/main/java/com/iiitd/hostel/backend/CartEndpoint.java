package com.iiitd.hostel.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.NotFoundException;
import com.google.appengine.api.datastore.Cursor;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.cmd.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;

import static com.googlecode.objectify.ObjectifyService.ofy;

/**
 * WARNING: This generated code is intended as a sample or starting point for using a
 * Google Cloud Endpoints RESTful API with an Objectify entity. It provides no data access
 * restrictions and no data validation.
 * <p/>
 * DO NOT deploy this code unchanged as part of a real application to real users.
 */
@Api(
        name = "cartApi",
        version = "v1",
        resource = "cart",
        namespace = @ApiNamespace(
                ownerDomain = "backend.hostel.iiitd.com",
                ownerName = "backend.hostel.iiitd.com",
                packagePath = ""
        )
)
public class CartEndpoint {

    private static final Logger logger = Logger.getLogger(CartEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Cart.class);
    }

    /**
     * Returns the {@link Cart} with the corresponding ID.
     *
     * @param id the ID of the entity to be retrieved
     * @return the entity with the corresponding ID
     * @throws NotFoundException if there is no {@code Cart} with the provided ID.
     */
    @ApiMethod(
            name = "get",
            path = "cart/{id}",
            httpMethod = ApiMethod.HttpMethod.GET)
    public Cart get(@Named("id") Long id) throws NotFoundException {
        logger.info("Getting Cart with ID: " + id);
        Cart cart = ofy().load().type(Cart.class).id(id).now();
        if (cart == null) {
            throw new NotFoundException("Could not find Cart with ID: " + id);
        }
        return cart;
    }

    /**
     * Inserts a new {@code Cart}.
     */
    @ApiMethod(
            name = "insert",
            path = "cart",
            httpMethod = ApiMethod.HttpMethod.POST)
    public Cart insert(Cart cart) {
        // Typically in a RESTful API a POST does not have a known ID (assuming the ID is used in the resource path).
        // You should validate that cart.id has not been set. If the ID type is not supported by the
        // Objectify ID generator, e.g. long or String, then you should generate the unique ID yourself prior to saving.
        //
        // If your client provides the ID then you should probably use PUT instead.
        ofy().save().entity(cart).now();
        logger.info("Created Cart with ID: " + cart.getId());

        return ofy().load().entity(cart).now();
    }

    /**
     * Updates an existing {@code Cart}.
     *
     * @param id   the ID of the entity to be updated
     * @param cart the desired state of the entity
     * @return the updated version of the entity
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Cart}
     */
    @ApiMethod(
            name = "update",
            path = "cart/{id}",
            httpMethod = ApiMethod.HttpMethod.PUT)
    public Cart update(@Named("id") Long id, Cart cart) throws NotFoundException {
        // TODO: You should validate your ID parameter against your resource's ID here.
        checkExists(id);
        ofy().save().entity(cart).now();
        logger.info("Updated Cart: " + cart);
        return ofy().load().entity(cart).now();
    }

    /**
     * Deletes the specified {@code Cart}.
     *
     * @param id the ID of the entity to delete
     * @throws NotFoundException if the {@code id} does not correspond to an existing
     *                           {@code Cart}
     */
    @ApiMethod(
            name = "remove",
            path = "cart/{id}",
            httpMethod = ApiMethod.HttpMethod.DELETE)
    public void remove(@Named("id") Long id) throws NotFoundException {
        checkExists(id);
        ofy().delete().type(Cart.class).id(id).now();
        logger.info("Deleted Cart with ID: " + id);
    }

    /**
     * List all entities.
     *
     * @param cursor used for pagination to determine which page to return
     * @param limit  the maximum number of entries to return
     * @return a response that encapsulates the result list and the next page token/cursor
     */
    @ApiMethod(
            name = "list",
            path = "cart",
            httpMethod = ApiMethod.HttpMethod.GET)
    public CollectionResponse<Cart> list(@Nullable @Named("cursor") String cursor, @Nullable @Named("limit") Integer limit) {
        limit = limit == null ? DEFAULT_LIST_LIMIT : limit;
        Query<Cart> query = ofy().load().type(Cart.class).limit(limit);
        if (cursor != null) {
            query = query.startAt(Cursor.fromWebSafeString(cursor));
        }
        QueryResultIterator<Cart> queryIterator = query.iterator();
        List<Cart> cartList = new ArrayList<Cart>(limit);
        while (queryIterator.hasNext()) {
            cartList.add(queryIterator.next());
        }
        return CollectionResponse.<Cart>builder().setItems(cartList).setNextPageToken(queryIterator.getCursor().toWebSafeString()).build();
    }

    private void checkExists(Long id) throws NotFoundException {
        try {
            ofy().load().type(Cart.class).id(id).safe();
        } catch (com.googlecode.objectify.NotFoundException e) {
            throw new NotFoundException("Could not find Cart with ID: " + id);
        }
    }
}