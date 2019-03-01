package com.solutionavenues.deedee.model.response;

import java.util.List;

/**
 * Created by brsoft on 5/7/17.
 */

public class LatLngFromAddressModel {

    /**
     * results : [{"address_components":[{"long_name":"Gurjar Ki Thadi","short_name":"Gurjar Ki Thadi","types":["political","sublocality","sublocality_level_1"]},{"long_name":"Jaipur","short_name":"Jaipur","types":["locality","political"]},{"long_name":"Jaipur","short_name":"Jaipur","types":["administrative_area_level_2","political"]},{"long_name":"Rajasthan","short_name":"RJ","types":["administrative_area_level_1","political"]},{"long_name":"India","short_name":"IN","types":["country","political"]}],"formatted_address":"Gurjar Ki Thadi, Jaipur, Rajasthan, India","geometry":{"bounds":{"northeast":{"lat":26.8767917,"lng":75.77047639999999},"southwest":{"lat":26.873025,"lng":75.7661701}},"location":{"lat":26.8749372,"lng":75.7683995},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":26.8767917,"lng":75.77047639999999},"southwest":{"lat":26.873025,"lng":75.7661701}}},"place_id":"ChIJvaS4jVO0bTkR3FS6VtT8kws","types":["political","sublocality","sublocality_level_1"]}]
     * status : OK
     */

    private String status;
    private List<ResultsBean> results;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * address_components : [{"long_name":"Gurjar Ki Thadi","short_name":"Gurjar Ki Thadi","types":["political","sublocality","sublocality_level_1"]},{"long_name":"Jaipur","short_name":"Jaipur","types":["locality","political"]},{"long_name":"Jaipur","short_name":"Jaipur","types":["administrative_area_level_2","political"]},{"long_name":"Rajasthan","short_name":"RJ","types":["administrative_area_level_1","political"]},{"long_name":"India","short_name":"IN","types":["country","political"]}]
         * formatted_address : Gurjar Ki Thadi, Jaipur, Rajasthan, India
         * geometry : {"bounds":{"northeast":{"lat":26.8767917,"lng":75.77047639999999},"southwest":{"lat":26.873025,"lng":75.7661701}},"location":{"lat":26.8749372,"lng":75.7683995},"location_type":"APPROXIMATE","viewport":{"northeast":{"lat":26.8767917,"lng":75.77047639999999},"southwest":{"lat":26.873025,"lng":75.7661701}}}
         * place_id : ChIJvaS4jVO0bTkR3FS6VtT8kws
         * types : ["political","sublocality","sublocality_level_1"]
         */

        private String formatted_address;
        private GeometryBean geometry;
        private String place_id;
        private List<AddressComponentsBean> address_components;
        private List<String> types;

        public String getFormatted_address() {
            return formatted_address;
        }

        public void setFormatted_address(String formatted_address) {
            this.formatted_address = formatted_address;
        }

        public GeometryBean getGeometry() {
            return geometry;
        }

        public void setGeometry(GeometryBean geometry) {
            this.geometry = geometry;
        }

        public String getPlace_id() {
            return place_id;
        }

        public void setPlace_id(String place_id) {
            this.place_id = place_id;
        }

        public List<AddressComponentsBean> getAddress_components() {
            return address_components;
        }

        public void setAddress_components(List<AddressComponentsBean> address_components) {
            this.address_components = address_components;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public static class GeometryBean {
            /**
             * bounds : {"northeast":{"lat":26.8767917,"lng":75.77047639999999},"southwest":{"lat":26.873025,"lng":75.7661701}}
             * location : {"lat":26.8749372,"lng":75.7683995}
             * location_type : APPROXIMATE
             * viewport : {"northeast":{"lat":26.8767917,"lng":75.77047639999999},"southwest":{"lat":26.873025,"lng":75.7661701}}
             */

            private BoundsBean bounds;
            private LocationBean location;
            private String location_type;
            private ViewportBean viewport;

            public BoundsBean getBounds() {
                return bounds;
            }

            public void setBounds(BoundsBean bounds) {
                this.bounds = bounds;
            }

            public LocationBean getLocation() {
                return location;
            }

            public void setLocation(LocationBean location) {
                this.location = location;
            }

            public String getLocation_type() {
                return location_type;
            }

            public void setLocation_type(String location_type) {
                this.location_type = location_type;
            }

            public ViewportBean getViewport() {
                return viewport;
            }

            public void setViewport(ViewportBean viewport) {
                this.viewport = viewport;
            }

            public static class BoundsBean {
                /**
                 * northeast : {"lat":26.8767917,"lng":75.77047639999999}
                 * southwest : {"lat":26.873025,"lng":75.7661701}
                 */

                private NortheastBean northeast;
                private SouthwestBean southwest;

                public NortheastBean getNortheast() {
                    return northeast;
                }

                public void setNortheast(NortheastBean northeast) {
                    this.northeast = northeast;
                }

                public SouthwestBean getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(SouthwestBean southwest) {
                    this.southwest = southwest;
                }

                public static class NortheastBean {
                    /**
                     * lat : 26.8767917
                     * lng : 75.77047639999999
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                public static class SouthwestBean {
                    /**
                     * lat : 26.873025
                     * lng : 75.7661701
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }

            public static class LocationBean {
                /**
                 * lat : 26.8749372
                 * lng : 75.7683995
                 */

                private double lat;
                private double lng;

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public double getLng() {
                    return lng;
                }

                public void setLng(double lng) {
                    this.lng = lng;
                }
            }

            public static class ViewportBean {
                /**
                 * northeast : {"lat":26.8767917,"lng":75.77047639999999}
                 * southwest : {"lat":26.873025,"lng":75.7661701}
                 */

                private NortheastBeanX northeast;
                private SouthwestBeanX southwest;

                public NortheastBeanX getNortheast() {
                    return northeast;
                }

                public void setNortheast(NortheastBeanX northeast) {
                    this.northeast = northeast;
                }

                public SouthwestBeanX getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(SouthwestBeanX southwest) {
                    this.southwest = southwest;
                }

                public static class NortheastBeanX {
                    /**
                     * lat : 26.8767917
                     * lng : 75.77047639999999
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }

                public static class SouthwestBeanX {
                    /**
                     * lat : 26.873025
                     * lng : 75.7661701
                     */

                    private double lat;
                    private double lng;

                    public double getLat() {
                        return lat;
                    }

                    public void setLat(double lat) {
                        this.lat = lat;
                    }

                    public double getLng() {
                        return lng;
                    }

                    public void setLng(double lng) {
                        this.lng = lng;
                    }
                }
            }
        }

        public static class AddressComponentsBean {
            /**
             * long_name : Gurjar Ki Thadi
             * short_name : Gurjar Ki Thadi
             * types : ["political","sublocality","sublocality_level_1"]
             */

            private String long_name;
            private String short_name;
            private List<String> types;

            public String getLong_name() {
                return long_name;
            }

            public void setLong_name(String long_name) {
                this.long_name = long_name;
            }

            public String getShort_name() {
                return short_name;
            }

            public void setShort_name(String short_name) {
                this.short_name = short_name;
            }

            public List<String> getTypes() {
                return types;
            }

            public void setTypes(List<String> types) {
                this.types = types;
            }
        }
    }
}
