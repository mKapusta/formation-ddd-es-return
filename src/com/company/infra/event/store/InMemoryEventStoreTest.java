package com.company.infra.event.store;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InMemoryEventStoreTest {

    @Test
    void returnEvent_whenEventStored() throws NumeroDeSequenceInvalide {
        // given
        var eventStore = new InMemoryEventStore<String, DumbEvent>();
        eventStore.addEvent("", new DumbEvent("Ouii"), 0);

        // when
        List<DumbEvent> events = eventStore.getEvents();

        //then
        assertEquals(List.of(new DumbEvent("Ouii")), events);
    }

    @Test
    void returnEvents_whenEventsStored() throws NumeroDeSequenceInvalide {
        // given
        var eventStore = new InMemoryEventStore<String, DumbEvent>();
        eventStore.addEvent("", new DumbEvent("Ouii"), 0);
        eventStore.addEvent("", new DumbEvent("Noon"), 1);

        // when
        List<DumbEvent> events = eventStore.getEvents();

        //then
        assertEquals(List.of(new DumbEvent("Ouii"), new DumbEvent("Noon")), events);
    }

    @Test
    void returnEventsFromOneAggregate_whenMultipleAggregates() throws NumeroDeSequenceInvalide {
        // given
        var eventStore = new InMemoryEventStore<String, DumbEvent>();
        eventStore.addEvent("A", new DumbEvent("Ouii"), 0);
        eventStore.addEvent("B", new DumbEvent("Noon"), 0);

        // when
        List<DumbEvent> events = eventStore.getEvents("A");

        // then
        assertEquals(List.of(new DumbEvent("Ouii")), events);
    }

    @Test
    void returnEventsFromSecondAggregate_whenMultipleAggregates() throws NumeroDeSequenceInvalide {
        // given
        var eventStore = new InMemoryEventStore<String, DumbEvent>();
        eventStore.addEvent("A", new DumbEvent("Ouii"), 0);
        eventStore.addEvent("B", new DumbEvent("Noon"), 0);

        // when
        List<DumbEvent> events = eventStore.getEvents("B");

        // then
        assertEquals(List.of(new DumbEvent("Noon")), events);
    }

    @Test
    void throwsNumeroDeSequenceInvalide_whenWrongSequenceNumber() throws NumeroDeSequenceInvalide {
        // given
        var eventStore = new InMemoryEventStore<String, DumbEvent>();
        eventStore.addEvent("A", new DumbEvent("Ouii"), 0);

        // then
        assertThrows(
                NumeroDeSequenceInvalide.class,
                () -> {
                    eventStore.addEvent("B", new DumbEvent("Noon"), 1);
                }
        );
    }

    private class DumbEvent {
        private final String name;

        public DumbEvent(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            DumbEvent dumbEvent = (DumbEvent) o;
            return Objects.equals(name, dumbEvent.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }
    }
}
