package demorestapi

import spock.lang.Specification
import com.mongodb.MongoClient
import de.flapdoodle.embed.mongo.distribution.Version
import de.flapdoodle.embed.mongo.tests.MongodForTestsFactory
import grails.test.mixin.TestFor
import grails.test.mongodb.*

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Product)
class ProductSpec extends MongoSpec{

    def setup() {
    }

    def cleanup() {
    }

   
    void "test domain class validation"() {
    when:"A domain class is saved with invalid data"
    Product product = new Product(name: "", price: -2.0d)
    product.save()

    then:"There were errors and the data was not saved"
    product.hasErrors()
    product.errors.getFieldError('price')
    product.errors.getFieldError('name')
    Product.count() == 0

	when:"A valid domain is saved"
	product.name = 'Banana'
	product.price = 2.15d
	product.save()

	then:"The product was saved successfully"
	Product.count() == 1
	Product.first().price == 2.15d
    }
    @Override
    MongoClient createMongoClient() {
     MongodForTestsFactory.with(Version.Main.PRODUCTION).newMongo()
    }
}
