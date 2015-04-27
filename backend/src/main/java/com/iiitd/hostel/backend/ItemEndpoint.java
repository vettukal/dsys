package com.iiitd.hostel.backend;

import com.google.api.server.spi.config.Api;
import com.google.api.server.spi.config.ApiMethod;
import com.google.api.server.spi.config.ApiNamespace;
import com.google.api.server.spi.response.CollectionResponse;
import com.google.api.server.spi.response.ConflictException;
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
        name = "itemApi",
        version = "v1",
        resource = "item",
        namespace = @ApiNamespace(
                ownerDomain = "backend.hostel.iiitd.com",
                ownerName = "backend.hostel.iiitd.com",
                packagePath = ""
        )
)
public class ItemEndpoint {

    private static final Logger logger = Logger.getLogger(ItemEndpoint.class.getName());

    private static final int DEFAULT_LIST_LIMIT = 20;

    static {
        // Typically you would register this inside an OfyServive wrapper. See: https://code.google.com/p/objectify-appengine/wiki/BestPractices
        ObjectifyService.register(Item.class);
    }

    // Make sure to add this endpoint to your web.xml file if this is a web application.

    private static final Logger LOG = Logger.getLogger(ItemEndpoint.class.getName());

    public ItemEndpoint() {
    }

    /**
     * Return a collection of items
     *
     * @param count The number of items
     * @return a list of Items
     */
    @ApiMethod(name = "listItem")
    public CollectionResponse<Item> listItem(@Nullable @Named("cursor") String cursorString,
                                               @Nullable @Named("count") Integer count) {

        Query<Item> query = ofy().load().type(Item.class);
        if (count != null) query.limit(count);
        if (cursorString != null && cursorString != "") {
            query = query.startAt(Cursor.fromWebSafeString(cursorString));
        }

        List<Item> records = new ArrayList<Item>();
        QueryResultIterator<Item> iterator = query.iterator();
        int num = 0;
        while (iterator.hasNext()) {
            records.add(iterator.next());
            if (count != null) {
                num++;
                if (num == count) break;
            }
        }

        //Find the next cursor
        if (cursorString != null && cursorString != "") {
            Cursor cursor = iterator.getCursor();
            if (cursor != null) {
                cursorString = cursor.toWebSafeString();
            }
        }
        return CollectionResponse.<Item>builder().setItems(records).setNextPageToken(cursorString).build();
    }

    /**
     * This inserts a new <code>Item</code> object.
     *
     * @param item The object to be added.
     * @return The object to be added.
     */
    @ApiMethod(name = "insertItem")
    public Item insertItem(Item item) throws ConflictException {
        //If if is not null, then check if it exists. If yes, throw an Exception
        //that it is already present
        if (item.getId() != null) {
            if (findRecord(item.getId()) != null) {
                throw new ConflictException("Object already exists");
            }
        }
        //Since our @Id field is a Long, Objectify will generate a unique value for us
        //when we use put
        ofy().save().entity(item).now();
        return item;
    }

    /**
     * This updates an existing <code>Item</code> object.
     *
     * @param item The object to be added.
     * @return The object to be updated.
     */
    @ApiMethod(name = "updateItem")
    public Item updateItem(Item item) throws NotFoundException {
        if (findRecord(item.getId()) == null) {
            throw new NotFoundException("Item Record does not exist");
        }
        ofy().save().entity(item).now();
        return item;
    }

    /**
     * This deletes an existing <code>Item</code> object.
     *
     * @param id The id of the object to be deleted.
     */
    @ApiMethod(name = "removeItem")
    public void removeItem(@Named("id") Long id) throws NotFoundException {
        Item record = findRecord(id);
        if (record == null) {
            throw new NotFoundException("Item Record does not exist");
        }
        ofy().delete().entity(record).now();
    }

    //Private method to retrieve a <code>Item</code> record
    private Item findRecord(Long id) {
        return ofy().load().type(Item.class).id(id).now();
        //or return ofy().load().type(Item.class).filter("id",id).first.now();
    }
}