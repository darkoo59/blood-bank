package bloodcenter.address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query(value="SELECT * FROM Address a WHERE a.lat = ?1 AND a.lng = ?2",
    nativeQuery = true)
    Address getAddressByLatLng(double lat, double lng);

    @Query(value="SELECT country FROM address",
    nativeQuery = true)
    List<String> getAllCountries();

    @Query(value="SELECT city FROM address",
            nativeQuery = true)
    List<String> getAllCities();
}
