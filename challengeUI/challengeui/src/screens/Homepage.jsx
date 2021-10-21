import React, { useState, useEffect } from "react";
import "./Styles.scss";
import "antd/dist/antd.css";
import SearchByCoords from "../components/SearchByCoords";
import SearchByCity from "./../components/SearchByCity";
import { apiKey } from "./../apiKey";
import { urlForSearch, urlForSearchByCoords } from "./../Urls";

const axios = require("axios").default;

function Homepage() {
  const [search, setSearch] = useState("");
  const [latitude, setLatitude] = useState();
  const [longitude, setLongitude] = useState();
  const [cnt, setCnt] = useState();
  const [cityBySearch, setCityBySearch] = useState();
  const [citiesBySearchCoords, setCitiesBySearchCoords] = useState(undefined);

  const [errorCords, setErrorCords] = useState(false);
  const [errorSearch, setErrorSearch] = useState(false);

  useEffect(() => {
    if (search == "") {
      setCityBySearch(undefined);
      setErrorSearch(false);
    }
  }, [search]);

  const handleSearch = async (city) => {
    try {
      await axios
        .get(urlForSearch + city, {
          headers: {
            "x-api-key": apiKey,
          },
        })
        .then((response) => {
          setCityBySearch(response.data);
          setErrorSearch(false);
        });
    } catch (error) {
      if (error.response.status === 500) {
        setErrorSearch(true);
      }
    }
  };

  const handleSearchByCoords = async (latitude, longitude, cnt) => {
    try {
      await axios
        .get(
          urlForSearchByCoords + latitude + "&lon=" + longitude + "&cnt=" + cnt,
          {
            headers: {
              "x-api-key": apiKey,
            },
          }
        )
        .then((response) => {
          setCitiesBySearchCoords(response.data);
          setErrorCords(false);
        });
    } catch (error) {
      if (error.response.status === 500) {
        setErrorCords(true);
      }
    }
  };

  const handleClear = () => {
    setCitiesBySearchCoords(undefined);
    setLatitude("");
    setLongitude("");
    setCnt("");
    setErrorCords(false);
  };

  return (
    <div>
      <div className="header">
        <div className="header--container">
          <div className="header--container-title">
            <div className="header--title">Weather App</div>
          </div>

          <div className="header--subtitle">
            Here you can search the weather by the name of a city or by location
          </div>
        </div>
      </div>
      <SearchByCity
        search={search}
        handleSearch={handleSearch}
        setSearch={setSearch}
        cityBySearch={cityBySearch}
        errorSearch={errorSearch}
      ></SearchByCity>
      <SearchByCoords
        errorCords={errorCords}
        citiesBySearchCoords={citiesBySearchCoords}
        handleSearchByCoords={handleSearchByCoords}
        latitude={latitude}
        longitude={longitude}
        cnt={cnt}
        handleClear={handleClear}
        setLatitude={setLatitude}
        setLongitude={setLongitude}
        setCnt={setCnt}
      ></SearchByCoords>
    </div>
  );
}

export default Homepage;
