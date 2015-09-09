ejb-security:  Using Java EE Declarative Security to Control Access to EJB 3
====================
Author: Stelios Kousouris
Technologies: EJB, Security  
Summary: Shows how to use Java EE Declarative Security to Control Access to EJB 3  
Target Product: EAP  
Product Versions: EAP 6.1, EAP 6.2, EAP 6.3  


What is it?
-----------

AgencyManagerBean the secured EJB, with authorization provided by custom-ejb-security-domain and roles createRole, queryRole, modifyRole protecting the relevant operations and required by the propagated security context


@Stateless(name = "AgencyManager")
@org.jboss.ejb3.annotation.SecurityDomain(value = "custom-ejb-security-domain")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
public class AgencyManagerBean implements AgencyManager {


with the following methods

    @RolesAllowed("createRole")
    public String makeBooking(String bookingDetails) {

    }

    /**
     * obtain the number of existing bookings
     * 
     * @return the number of current bookings
     */
    @RolesAllowed("queryRole")
    public int getBookingCount() {

    }


    /**
     * Reset the booking count to zero
     */
    @RolesAllowed("modifyRole")
    public void reset() {

    }


